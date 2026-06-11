package com.bottelx.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

// ============================================

// BatchServiceImpl.java
// ============================================

import org.springframework.stereotype.Service;

import com.bottelx.dto.BatchRequest;
import com.bottelx.dto.BatchResponse;
import com.bottelx.entity.Batch;
import com.bottelx.entity.Product;
import com.bottelx.entity.QRCode;
import com.bottelx.repository.BatchRepository;
import com.bottelx.repository.ProductRepository;
import com.bottelx.repository.QRCodeRepository;
import com.bottelx.services.BatchService;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BatchServiceImpl
                implements BatchService {
        @Autowired
        private BatchRepository batchRepository;
        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private QRCodeRepository qrCodeRepository;

        @Override
        public BatchResponse createBatch(
                        UUID companyId,
                        BatchRequest request) {

                if (batchRepository
                                .existsByBatchNumberAndCompany_Id(
                                                request.getBatchNumber(),
                                                companyId)) {

                        throw new RuntimeException(
                                        "Batch number already exists");
                }

                Product product = productRepository
                                .findByIdAndCompany_Id(
                                                request.getProductId(),
                                                companyId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Product not found"));
                Batch batch = new Batch();

                batch.setBatchNumber(
                                request.getBatchNumber());

                batch.setQuantity(
                                request.getQuantity());

                batch.setRemainingQuantity(
                                request.getQuantity());

                batch.setManufacturingDate(
                                request.getManufacturingDate());

                batch.setExpiryDate(
                                request.getExpiryDate());

                batch.setProduct(product);

                batch.setActive(true);

                batch.setCreatedAt(
                                LocalDateTime.now());

                batch.setUpdatedAt(
                                LocalDateTime.now());

                batch.setCompany(product.getCompany());

                batchRepository.save(batch);

                return mapToResponse(batch);
        }

        @Override
        public List<BatchResponse> getAllBatches(
                        UUID companyId) {

                return batchRepository
                                .findByCompany_IdAndActiveTrue(companyId)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public BatchResponse getBatchById(
                        UUID companyId,
                        String id) {

                Batch batch = batchRepository
                                .findByIdAndCompany_Id(
                                                id,
                                                companyId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Batch not found"));

                return mapToResponse(batch);
        }

        @Override
        public BatchResponse updateBatch(
                        UUID companyId,
                        String id,
                        BatchRequest request) {

                Batch batch = batchRepository
                                .findByIdAndCompany_Id(
                                                id,
                                                companyId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Batch not found"));

                Product product = productRepository
                                .findByIdAndCompany_Id(
                                                request.getProductId(),
                                                companyId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Product not found"));

                batch.setBatchNumber(
                                request.getBatchNumber());

                batch.setQuantity(
                                request.getQuantity());

                batch.setManufacturingDate(
                                request.getManufacturingDate());

                batch.setExpiryDate(
                                request.getExpiryDate());

                batch.setProduct(product);

                batch.setUpdatedAt(
                                LocalDateTime.now());
                batch.setCompany(product.getCompany());

                batchRepository.save(batch);

                return mapToResponse(batch);
        }

        @Override
        public void deleteBatch(
                        UUID companyId,
                        String id) {

                Batch batch = batchRepository
                                .findByIdAndCompany_IdAndActiveTrue(
                                                id,
                                                companyId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Batch not found"));

                batch.setActive(false);

                List<QRCode> qrCodes = qrCodeRepository.findByBatch_Id(id);

                qrCodes.forEach(qr -> qr.setActive(false));

                qrCodeRepository.saveAll(qrCodes);

                batchRepository.save(batch);
        }

        @Override
        public void toggleStatus(
                        UUID companyId,
                        String id,
                        Boolean active) {

                Batch batch = batchRepository
                                .findByIdAndCompany_Id(
                                                id,
                                                companyId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Batch not found"));

                batch.setActive(active);

                batchRepository.save(batch);
        }

        // =========================================
        // MAPPER
        // =========================================

        private BatchResponse mapToResponse(
                        Batch batch) {

                BatchResponse response = new BatchResponse(null, null, null, null, null, null, null, null, null);

                response.setId(
                                batch.getId());

                response.setBatchNumber(
                                batch.getBatchNumber());

                response.setQuantity(
                                batch.getQuantity());

                response.setRemainingQuantity(
                                batch.getRemainingQuantity());

                response.setManufacturingDate(
                                batch.getManufacturingDate());

                response.setExpiryDate(
                                batch.getExpiryDate());

                response.setActive(
                                batch.getActive());

                response.setProductId(
                                batch.getProduct().getId());

                response.setProductName(
                                batch.getProduct().getProductName());

                return response;
        }
}
