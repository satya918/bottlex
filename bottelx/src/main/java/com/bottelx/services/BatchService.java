package com.bottelx.services;

// ============================================

// BatchService.java
// ============================================

import java.util.List;
import java.util.UUID;

import com.bottelx.dto.BatchRequest;
import com.bottelx.dto.BatchResponse;

public interface BatchService {

        BatchResponse createBatch(UUID companyId,
                        BatchRequest request);

        List<BatchResponse> getAllBatches(UUID companyId);

        BatchResponse getBatchById(UUID companyId,
                        String id);

        BatchResponse updateBatch(UUID companyId,
                        String id,
                        BatchRequest request);

        void deleteBatch(UUID companyId,
                        String id);

        void toggleStatus(UUID companyId,
                        String id,
                        Boolean active);
}
