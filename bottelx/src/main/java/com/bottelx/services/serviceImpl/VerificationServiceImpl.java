package com.bottelx.services.serviceImpl;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottelx.dto.VerifyQRRequest;
import com.bottelx.dto.VerifyQRResponse;
import com.bottelx.entity.QRCode;
import com.bottelx.entity.QRScanLog;
import com.bottelx.repository.QRCodeRepository;
import com.bottelx.repository.QRScanLogRepository;
import com.bottelx.services.VerificationService;

@Service
public class VerificationServiceImpl
                implements VerificationService {
        @Autowired
        private QRCodeRepository qrCodeRepository;
        @Autowired
        private QRScanLogRepository qrScanLogRepository;

       

        @Override
        public VerifyQRResponse verifyQRCode(
                        VerifyQRRequest request,
                        HttpServletRequest httpRequest) {

                String ip = httpRequest.getRemoteAddr();

                QRCode qr = qrCodeRepository
                                .findByQrCode(
                                                request.getQrCode())
                                .orElse(null);

                if (qr == null) {

                        saveLog(
                                        request.getQrCode(),
                                        "FAKE",
                                        true,
                                        ip,
                                        null);

                        return new VerifyQRResponse(
                                        false,
                                        true,
                                        null,
                                        null,
                                        "Fake Product");
                }

                if (!qr.isActive()) {

                        saveLog(
                                        qr.getQrCode(),
                                        "INACTIVE",
                                        true,
                                        ip,
                                        qr);

                        return new VerifyQRResponse(
                                        false,
                                        true,
                                        qr.getProduct().getProductName(),
                                        qr.getBatch().getBatchNumber(),
                                        "Inactive QR");
                }

                long scanCount = qrScanLogRepository
                                .countByQrCode(
                                                qr.getQrCode());

                boolean suspicious = scanCount >= 5;

                saveLog(
                                qr.getQrCode(),
                                suspicious
                                                ? "SUSPICIOUS"
                                                : "GENUINE",
                                suspicious,
                                ip,
                                qr);

                return new VerifyQRResponse(
                                true,
                                suspicious,
                                qr.getProduct().getProductName(),
                                qr.getBatch().getBatchNumber(),
                                suspicious
                                                ? "Suspicious Multiple Scans"
                                                : "Authentic Product");
        }

        private void saveLog(
                        String qrCode,
                        String status,
                        boolean suspicious,
                        String ip,
                        QRCode qr) {

                QRScanLog log = new QRScanLog();

                log.setQrCode(qrCode);

                log.setScanStatus(status);

                log.setSuspicious(suspicious);

                log.setIpAddress(ip);

                log.setQr(qr);

                qrScanLogRepository.save(log);
        }
}
