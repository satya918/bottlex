package com.bottelx.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bottelx.dto.CounterfeitAlertResponse;
import com.bottelx.entity.QRScanLog;

public interface QRScanLogRepository
        extends JpaRepository<QRScanLog, String> {

    long countByQrCode(String qrCode);

    long countByScanStatus(String status);

    List<QRScanLog> findTop10ByOrderByCreatedAtDesc();

    long countByCompanyId(UUID companyId);

    long countByCompany_IdAndScanStatus(UUID companyId, String scanStatus);

    @Query("""
            SELECT new com.bottelx.dto.CounterfeitAlertResponse(
                p.id,
                p.productName,
                l.city,
                COUNT(l),
                CASE
                WHEN COUNT(l) > 50 THEN 'CRITICAL'
                WHEN COUNT(l) > 20 THEN 'HIGH'
                WHEN COUNT(l) > 10 THEN 'MEDIUM'
                ELSE 'LOW'
            END
            )
            FROM QRScanLog l
            JOIN l.qr q
            JOIN q.product p
            WHERE l.scanStatus='SUSPICIOUS'
            AND p.company.id=:companyId
            GROUP BY p.id,p.productName,l.city
            ORDER BY COUNT(l) DESC
            """)
    List<CounterfeitAlertResponse> findCounterfeitAlerts(@Param("companyId") UUID companyId);

    List<UUID> findProductIdsByCompanyId(UUID companyId);

    long countByProductId(String id);

    long countFakeScansByProductId(String id);
}
