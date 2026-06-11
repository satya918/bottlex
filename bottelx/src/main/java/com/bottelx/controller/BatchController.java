package com.bottelx.controller;

import org.springframework.beans.factory.annotation.Autowired;

// ============================================

// BatchController.java
// ============================================

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.BatchRequest;
import com.bottelx.dto.BatchResponse;
import com.bottelx.services.BatchService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/batches")
public class BatchController {
        @Autowired
        private BatchService batchService;

        @PostMapping("/{companyId}")
        public ResponseEntity<BatchResponse> createBatch(
                        @PathVariable UUID companyId,
                        @RequestBody BatchRequest request) {

                return ResponseEntity.ok(
                                batchService.createBatch(
                                                companyId,
                                                request));
        }

        @GetMapping("/{companyId}")
        public ResponseEntity<List<BatchResponse>> getAllBatches(
                        @PathVariable UUID companyId) {

                return ResponseEntity.ok(
                                batchService.getAllBatches(companyId));
        }

        @GetMapping("/{companyId}/{id}")
        public ResponseEntity<BatchResponse> getBatchById(
                        @PathVariable UUID companyId,
                        @PathVariable String id) {

                return ResponseEntity.ok(
                                batchService.getBatchById(companyId, id));
        }

        @PutMapping("/{companyId}/{id}")
        public ResponseEntity<BatchResponse> updateBatch(
                        @PathVariable UUID companyId,
                        @PathVariable String id,
                        @RequestBody BatchRequest request) {

                return ResponseEntity.ok(
                                batchService.updateBatch(
                                                companyId,
                                                id,
                                                request));
        }

        @DeleteMapping("/{companyId}/{id}")
        public ResponseEntity<String> deleteBatch(
                        @PathVariable UUID companyId,
                        @PathVariable String id) {

                batchService.deleteBatch(companyId, id);

                return ResponseEntity.ok(
                                "Batch deleted successfully");
        }

        @PatchMapping("/{companyId}/{id}/status")
        public ResponseEntity<String> toggleStatus(
                        @PathVariable UUID companyId,
                        @PathVariable String id,
                        @RequestParam Boolean active) {

                batchService.toggleStatus(
                                companyId,
                                id,
                                active);

                return ResponseEntity.ok(
                                "Batch status updated");
        }
}
