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
import com.bottelx.repository.BatchRepository;
import com.bottelx.repository.ProductRepository;
import com.bottelx.services.BatchService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BatchServiceImpl
                implements BatchService {
        @Autowired
        private BatchRepository batchRepository;
        @Autowired
        private ProductRepository productRepository;

        public BatchServiceImpl(
                        BatchRepository batchRepository,
                        ProductRepository productRepository) {
                this.batchRepository = batchRepository;
                this.productRepository = productRepository;
        }

        @Override
        public BatchResponse createBatch(
                        BatchRequest request) {

                if (batchRepository.existsByBatchNumber(
                                request.getBatchNumber())) {

                        throw new RuntimeException(
                                        "Batch number already exists");
                }

                Product product = productRepository.findById(
                                request.getProductId()).orElseThrow(
                                                () -> new RuntimeException(
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

                batchRepository.save(batch);

                return mapToResponse(batch);
        }

        @Override
        public List<BatchResponse> getAllBatches() {

                return batchRepository.findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public BatchResponse getBatchById(
                        String id) {

                Batch batch = batchRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Batch not found"));

                return mapToResponse(batch);
        }

        @Override
        public BatchResponse updateBatch(
                        String id,
                        BatchRequest request) {

                Batch batch = batchRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Batch not found"));

                Product product = productRepository.findById(
                                request.getProductId()).orElseThrow(
                                                () -> new RuntimeException(
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

                batchRepository.save(batch);

                return mapToResponse(batch);
        }

        @Override
        public void deleteBatch(
                        String id) {

                batchRepository.deleteById(id);
        }

        @Override
        public void toggleStatus(
                        String id,
                        Boolean active) {

                Batch batch = batchRepository.findById(id)
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
