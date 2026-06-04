package com.bottelx.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.QRScanLog;

public interface QRScanLogRepository
        extends JpaRepository<QRScanLog, String> {

    long countByQrCode(String qrCode);

     long countByScanStatus(String status);

    List<QRScanLog>
    findTop10ByOrderByCreatedAtDesc();
}
