package com.bottelx.repository;

import java.util.Collection;

// ============================================
// BatchRepository.java
// ============================================



import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.dto.CounterfeitAlertResponse;
import com.bottelx.entity.Batch;

public interface BatchRepository
        extends JpaRepository<Batch, String> {

    boolean existsByBatchNumber(
            String batchNumber
    );

    Collection<CounterfeitAlertResponse> findTop10ByOrderByCreatedAtDesc();
}
