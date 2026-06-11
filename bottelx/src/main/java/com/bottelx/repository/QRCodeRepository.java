package com.bottelx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.QRCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QRCodeRepository
                extends JpaRepository<QRCode, String> {

        Optional<QRCode> findByQrCode(String qrCode);

        Page<QRCode> findByCompany_IdAndActiveTrue(UUID companyId, Pageable pageable);

        Optional<QRCode> findByIdAndCompany_Id(
                        String id,
                        UUID companyId);

        long countByBatch_Id(String batchId);

        List<QRCode> findByBatch_Id(String batchId);
}
