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

@RestController
@RequestMapping("/api/admin/batches")
public class BatchController {
@Autowired
    private  BatchService batchService;

    public BatchController(
            BatchService batchService
    ) {
        this.batchService = batchService;
    }

    // =========================================
    // CREATE BATCH
    // =========================================

    @PostMapping
    public ResponseEntity<BatchResponse>
    createBatch(
            @RequestBody
            BatchRequest request
    ) {

        return ResponseEntity.ok(
                batchService.createBatch(
                        request
                )
        );
    }

    // =========================================
    // GET ALL BATCHES
    // =========================================

    @GetMapping
    public ResponseEntity<List<BatchResponse>>
    getAllBatches() {

        return ResponseEntity.ok(
                batchService.getAllBatches()
        );
    }

    // =========================================
    // GET BATCH BY ID
    // =========================================

    @GetMapping("/{id}")
    public ResponseEntity<BatchResponse>
    getBatchById(
            @PathVariable String id
    ) {

        return ResponseEntity.ok(
                batchService.getBatchById(id)
        );
    }

    // =========================================
    // UPDATE BATCH
    // =========================================

    @PutMapping("/{id}")
    public ResponseEntity<BatchResponse>
    updateBatch(
            @PathVariable String id,
            @RequestBody BatchRequest request
    ) {

        return ResponseEntity.ok(
                batchService.updateBatch(
                        id,
                        request
                )
        );
    }

    // =========================================
    // DELETE BATCH
    // =========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deleteBatch(
            @PathVariable String id
    ) {

        batchService.deleteBatch(id);

        return ResponseEntity.ok(
                "Batch deleted successfully"
        );
    }

    // =========================================
    // TOGGLE STATUS
    // =========================================

    @PatchMapping("/{id}/status")
    public ResponseEntity<String>
    toggleStatus(
            @PathVariable String id,
            @RequestParam Boolean active
    ) {

        batchService.toggleStatus(
                id,
                active
        );

        return ResponseEntity.ok(
                "Batch status updated"
        );
    }
}
