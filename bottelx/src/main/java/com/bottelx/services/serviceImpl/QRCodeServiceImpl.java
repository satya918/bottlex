package com.bottelx.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bottelx.dto.QRCodeRequest;
import com.bottelx.dto.QRCodeResponse;
import com.bottelx.dto.QRGenerationResponse;
import com.bottelx.entity.Batch;
import com.bottelx.entity.Product;
import com.bottelx.entity.QRCode;
import com.bottelx.repository.BatchRepository;
import com.bottelx.repository.ProductRepository;
import com.bottelx.repository.QRCodeRepository;
import com.bottelx.services.QRCodeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QRCodeServiceImpl
                implements QRCodeService {
        @Autowired
        private QRCodeRepository qrCodeRepository;
        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private BatchRepository batchRepository;

        @Override
        public QRGenerationResponse  generateQRCode(
                        QRCodeRequest request,
                        UUID companyId) {

                Product product = productRepository
                                .findByIdAndCompany_Id(
                                                request.getProductId(),
                                                companyId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                Batch batch = batchRepository
                                .findByIdAndCompany_Id(
                                                request.getBatchId(),
                                                companyId)
                                .orElseThrow(() -> new RuntimeException("Batch not found"));

                // Prevent duplicate generation
                if (qrCodeRepository.countByBatch_Id(batch.getId()) > 0) {
                        throw new RuntimeException(
                                        "QR codes already generated for this batch");
                }

                Integer quantity = batch.getQuantity();

                List<QRCode> qrCodes = new ArrayList<>();

                for (int i = 1; i <= quantity; i++) {

                        QRCode qr = new QRCode();

                        String serialNumber = batch.getBatchNumber()
                                        + "-"
                                        + String.format("%06d", i);

                        qr.setQrCode(
                                        UUID.randomUUID().toString());
                        qr.setQrType(request.getQrType());
                        qr.setSerialNumber(serialNumber);

                        qr.setProduct(product);

                        qr.setBatch(batch);

                        qr.setCompany(product.getCompany());

                        qrCodes.add(qr);
                }

                List<QRCode> savedQrs = qrCodeRepository.saveAll(qrCodes);

                return new QRGenerationResponse(
                                batch.getId(),
                                batch.getBatchNumber(),
                                qrCodes.size(),
                                "QR codes generated successfully");
        }

        @Override
        public Page<QRCodeResponse> getAll(UUID companyId, Pageable pageable) {

                return qrCodeRepository
                                .findByCompany_IdAndActiveTrue(companyId, pageable)
                                .map(this::map);
        }

        @Override
        public QRCodeResponse scanQRCode(
                        String qrCode) {

                QRCode qr = qrCodeRepository
                                .findByQrCode(qrCode)
                                .orElseThrow();

                qr.setLastScannedAt(
                                LocalDateTime.now());

                return map(
                                qrCodeRepository.save(qr));
        }

        @Override
        public void toggleStatus(UUID companyId,
                        String id,
                        boolean active) {

                QRCode qr = qrCodeRepository
                                .findByIdAndCompany_Id(
                                                id,
                                                companyId)
                                .orElseThrow(() -> new RuntimeException("QR Code not found"));

                qr.setActive(active);

                qrCodeRepository.save(qr);
        }

        @Override
        public void deleteQRCode(UUID companyId,
                        String id) {

                QRCode qr = qrCodeRepository
                                .findByIdAndCompany_Id(
                                                id,
                                                companyId)
                                .orElseThrow(() -> new RuntimeException("QR Code not found"));

                qrCodeRepository.delete(qr);
        }

        private QRCodeResponse map(
                        QRCode qr) {

                return new QRCodeResponse(
                                qr.getId(),
                                qr.getQrCode(),
                                qr.getQrType(),
                                qr.isActive(),
                                qr.getProduct().getProductName(),
                                qr.getBatch().getBatchNumber(),
                                qr.getCreatedAt(),
                                qr.getLastScannedAt());
        }
}
