package com.bottelx.services.serviceImpl;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bottelx.dto.VerifyQRRequest;
import com.bottelx.dto.VerifyQRResponse;
import com.bottelx.entity.Company;
import com.bottelx.entity.Product;
import com.bottelx.entity.QRCode;
import com.bottelx.entity.QRScanLog;
import com.bottelx.entity.ScanHistory;
import com.bottelx.entity.User;
import com.bottelx.enums.VerificationStatus;
import com.bottelx.repository.QRCodeRepository;
import com.bottelx.repository.QRScanLogRepository;
import com.bottelx.repository.ScanHistoryRepository;
import com.bottelx.repository.UserRepository;
import com.bottelx.services.VerificationService;

@Service
public class VerificationServiceImpl implements VerificationService {

        @Autowired
        private QRCodeRepository qrCodeRepository;

        @Autowired
        private QRScanLogRepository qrScanLogRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ScanHistoryRepository scanHistoryRepository;

        @Transactional
        @Override
        public VerifyQRResponse verifyQRCode(
                        VerifyQRRequest request,
                        HttpServletRequest httpRequest, Authentication authentication) {

                System.out.println(request.getLatitude() + "," + request.getLongitude() + "," + request.getCity() + ","
                                + request.getState() + "," + request.getCountry());

                if (authentication == null) {
                        throw new AccessDeniedException(
                                        "Authentication required");
                }

                String ip = httpRequest.getRemoteAddr();

                if (request.getQrCode() == null
                                || request.getQrCode().isBlank()) {
                        saveLog(
                                        "EMPTY_QR",
                                        VerificationStatus.COUNTERFEIT.name(),
                                        true,
                                        ip,
                                        null,
                                        null,
                                        null,
                                        request.getLatitude(),
                                        request.getLongitude(),
                                        request.getCity(),
                                        request.getState(),
                                        request.getCountry());
                        return new VerifyQRResponse(
                                        VerificationStatus.COUNTERFEIT.name(),
                                        null,
                                        null,
                                        "Invalid QR code.");
                }

                QRCode qr = qrCodeRepository
                                .findByQrCode(request.getQrCode())
                                .orElse(null);

                // COUNTERFEIT QR
                if (qr == null) {

                        saveLog(
                                        request.getQrCode(),
                                        VerificationStatus.COUNTERFEIT.name(),
                                        true,
                                        ip,
                                        null,
                                        null,
                                        null,
                                        request.getLatitude(),
                                        request.getLongitude(),
                                        request.getCity(),
                                        request.getState(),
                                        request.getCountry());

                        return new VerifyQRResponse(
                                        VerificationStatus.COUNTERFEIT.name(),
                                        null,
                                        null,
                                        "This QR code could not be verified. The product may be counterfeit.");
                }
                String productName = qr.getProduct() != null
                                ? qr.getProduct().getProductName()
                                : "Unknown Product";

                String batchNumber = qr.getBatch() != null
                                ? qr.getBatch().getBatchNumber()
                                : "Unknown Batch";
                // INACTIVE QR
                if (!qr.isActive()) {

                        saveLog(
                                        qr.getQrCode(),
                                        VerificationStatus.INACTIVE.name(),
                                        true,
                                        ip,
                                        qr,
                                        qr.getProduct(),
                                        qr.getCompany(),
                                        request.getLatitude(),
                                        request.getLongitude(),
                                        request.getCity(),
                                        request.getState(),
                                        request.getCountry());

                        return new VerifyQRResponse(
                                        VerificationStatus.INACTIVE.name(),
                                        productName,
                                        batchNumber,
                                        "This product is no longer active in the manufacturer's system.");
                }

                if (qr.getBatch() != null
                                && qr.getBatch().getExpiryDate() != null
                                && qr.getBatch()
                                                .getExpiryDate()
                                                .isBefore(LocalDate.now())) {

                        saveLog(
                                        qr.getQrCode(),
                                        VerificationStatus.EXPIRED.name(),
                                        true,
                                        ip,
                                        qr,
                                        qr.getProduct(),
                                        qr.getCompany(),
                                        request.getLatitude(),
                                        request.getLongitude(),
                                        request.getCity(),
                                        request.getState(),
                                        request.getCountry());

                        return new VerifyQRResponse(
                                        VerificationStatus.EXPIRED.name(),
                                        productName,
                                        batchNumber,
                                        "This product has expired and should not be consumed.");
                }

                long uniqueUserCount = scanHistoryRepository
                                .countDistinctUsersByQrCodeId(
                                                qr.getId());

                boolean suspicious = uniqueUserCount >= 2;

                User user = userRepository
                                .findByUserNameIgnoreCaseOrEmailIgnoreCase(
                                                authentication.getName(),
                                                authentication.getName())
                                .orElseThrow(() -> new AccessDeniedException(
                                                "User account not found"));

                boolean alreadyScanned = scanHistoryRepository
                                .existsByUserAndQrCode(
                                                user,
                                                qr);

                // SUSPICIOUS QR
                if (suspicious) {

                        saveLog(
                                        qr.getQrCode(),
                                        VerificationStatus.SUSPICIOUS.name(),
                                        true,
                                        ip,
                                        qr,
                                        qr.getProduct(),
                                        qr.getCompany(),
                                        request.getLatitude(),
                                        request.getLongitude(),
                                        request.getCity(),
                                        request.getState(),
                                        request.getCountry());

                        return new VerifyQRResponse(
                                        VerificationStatus.SUSPICIOUS.name(),
                                        productName,
                                        batchNumber,
                                        "This product has been verified by multiple users. Please verify the source before purchase or use.");
                }

                if (alreadyScanned) {

                        saveLog(
                                        qr.getQrCode(),
                                        VerificationStatus.SAME_USER_DUPLICATE_SCAN.name(),
                                        false,
                                        ip,
                                        qr,
                                        qr.getProduct(),
                                        qr.getCompany(),
                                        request.getLatitude(),
                                        request.getLongitude(),
                                        request.getCity(),
                                        request.getState(),
                                        request.getCountry());

                        return new VerifyQRResponse(
                                        VerificationStatus.SAME_USER_DUPLICATE_SCAN.name(),
                                        productName,
                                        batchNumber,
                                        "You have already verified this product using your account.");
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
                                qr.getQrCode(),
                                VerificationStatus.AUTHENTIC.name(),
                                false,
                                ip,
                                qr,
                                qr.getProduct(),
                                qr.getCompany(),
                                request.getLatitude(),
                                request.getLongitude(),
                                request.getCity(),
                                request.getState(),
                                request.getCountry());

                return new VerifyQRResponse(
                                VerificationStatus.AUTHENTIC.name(),
                                productName,
                                batchNumber,
                                "This product is genuine and successfully verified by BottleX.");
        }

        private void saveLog(
                        String qrCode,
                        String status,
                        boolean suspicious,
                        String ip,
                        QRCode qr,
                        Product product,
                        Company company,
                        double latitude,
                        double longitude,
                        String city,
                        String state,
                        String country) {

                QRScanLog log = new QRScanLog();

                log.setQrCode(qrCode);
                log.setScanStatus(status);
                log.setSuspicious(suspicious);
                log.setIpAddress(ip);
                log.setQr(qr);
                log.setProduct(product);
                log.setCompany(company);
                log.setLatitude(latitude);
                log.setLongitude(longitude);
                log.setCity(city);
                log.setState(state);
                log.setCountry(country);

                qrScanLogRepository.save(log);
        }
}