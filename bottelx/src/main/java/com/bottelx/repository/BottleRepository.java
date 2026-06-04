package com.bottelx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.Bottle;

import java.util.Optional;
import java.util.UUID;

public interface BottleRepository
        extends JpaRepository<Bottle, UUID> {

    Optional<Bottle> findByQrCode(
            String qrCode
    );
}
