package com.bottelx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bottelx.entity.QRCode;
import com.bottelx.entity.ScanHistory;
import com.bottelx.entity.User;

import java.util.UUID;

public interface ScanHistoryRepository
                extends JpaRepository<ScanHistory, UUID> {

        boolean existsByUserAndQrCode(
                        User user,
                        QRCode qrCode);

        @Query("""
                        SELECT COUNT(DISTINCT sh.user.id)
                        FROM ScanHistory sh
                        WHERE sh.qrCode.id = :qrId
                        """)
        long countDistinctUsersByQrCodeId(
                        @Param("qrId") String qrId);
}