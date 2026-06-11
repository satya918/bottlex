package com.bottelx.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.Batch;

public interface BatchRepository
                extends JpaRepository<Batch, String> {

        boolean existsByBatchNumberAndCompany_Id(
                        String batchNumber,
                        UUID companyId);

        Optional<Batch> findByIdAndCompany_Id(
                        String id,
                        UUID companyId);

        Optional<Batch> findByIdAndCompany_IdAndActiveTrue(
                        String id,
                        UUID companyId);

        List<Batch> findByCompany_IdAndActiveTrue(
                        UUID companyId);

        List<Batch> findTop10ByCompany_IdOrderByCreatedAtDesc(UUID companyId);
}
