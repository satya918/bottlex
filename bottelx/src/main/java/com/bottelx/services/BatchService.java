package com.bottelx.services;

// ============================================
// BatchService.java
// ============================================



import java.util.List;

import com.bottelx.dto.BatchRequest;
import com.bottelx.dto.BatchResponse;

public interface BatchService {

    BatchResponse createBatch(
            BatchRequest request
    );

    List<BatchResponse> getAllBatches();

    BatchResponse getBatchById(
            String id
    );

    BatchResponse updateBatch(
            String id,
            BatchRequest request
    );

    void deleteBatch(
            String id
    );

    void toggleStatus(
            String id,
            Boolean active
    );
}
