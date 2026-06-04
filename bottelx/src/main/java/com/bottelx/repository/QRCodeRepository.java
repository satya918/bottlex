package com.bottelx.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.QRCode;

import java.util.Optional;

public interface QRCodeRepository
        extends JpaRepository<QRCode, String> {

    Optional<QRCode> findByQrCode(String qrCode);
}
