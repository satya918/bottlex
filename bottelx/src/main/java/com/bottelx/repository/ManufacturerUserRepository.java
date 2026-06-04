package com.bottelx.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.ManufacturerUser;


public interface ManufacturerUserRepository
extends JpaRepository<ManufacturerUser, Long> {

    Optional<ManufacturerUser>
    findByEmail(String email);
}
