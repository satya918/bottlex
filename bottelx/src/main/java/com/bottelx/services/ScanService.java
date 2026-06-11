package com.bottelx.services;

import com.bottelx.dto.QrScanRequest;
import com.bottelx.entity.QRCode;
import com.bottelx.entity.QRScanLog;
import com.bottelx.entity.ScanHistory;
import com.bottelx.entity.User;
import com.bottelx.repository.QRCodeRepository;
import com.bottelx.repository.QRScanLogRepository;
import com.bottelx.repository.ScanHistoryRepository;
import com.bottelx.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScanService {

        @Autowired
        private QRCodeRepository qrCodeRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ScanHistoryRepository scanHistoryRepository;

        @Autowired
        private QRScanLogRepository qrScanLogRepository;

        public String verifyQr(
                        QrScanRequest request,
                        Authentication authentication,
                        String ipAddress) {

                QRCode qr = qrCodeRepository
                                .findByQrCode(request.getQrCode())
                                .orElse(null);

                if (qr == null) {

                        saveFailedLog(
                                        request.getQrCode(),
                                        ipAddress);

                        return "Invalid QR Code";
                }

                if (!qr.isActive()) {

                        saveLog(
                                        qr,
                                        "INACTIVE",
                                        true,
                                        ipAddress);

                        return "QR Code is inactive";
                }

                User user = userRepository
                                .findByUserNameIgnoreCaseOrEmailIgnoreCase(
                                                authentication.getName(),
                                                authentication.getName())
                                .orElseThrow();

                boolean alreadyScanned = scanHistoryRepository
                                .existsByUserAndQrCode(
                                                user,
                                                qr);

                if (alreadyScanned) {

                        saveLog(
                                        qr,
                                        "DUPLICATE_SCAN",
                                        true,
                                        ipAddress);

                        return "QR already scanned";
                }

                qr.setLastScannedAt(
                                LocalDateTime.now());

                qrCodeRepository.save(qr);

                ScanHistory history = new ScanHistory();

                history.setUser(user);
                history.setQrCode(qr);
                history.setScannedAt(
                                LocalDateTime.now());

                scanHistoryRepository.save(history);

                saveLog(
                                qr,
                                "SUCCESS",
                                false,
                                ipAddress);

                return "Authentic Product Verified : "
                                + qr.getProduct().getProductName()
                                + " | Batch : "
                                + qr.getBatch().getBatchNumber();
        }

        private void saveLog(
                        QRCode qr,
                        String status,
                        boolean suspicious,
                        String ipAddress) {

                QRScanLog log = new QRScanLog();

                log.setQr(qr);
                log.setQrCode(qr.getQrCode());
                log.setScanStatus(status);
                log.setSuspicious(suspicious);
                log.setIpAddress(ipAddress);

                qrScanLogRepository.save(log);
        }

        private void saveFailedLog(
                        String qrCode,
                        String ipAddress) {

                QRScanLog log = new QRScanLog();

                log.setQrCode(qrCode);
                log.setScanStatus("INVALID");
                log.setSuspicious(true);
                log.setIpAddress(ipAddress);

                qrScanLogRepository.save(log);
        }
}