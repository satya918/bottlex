package com.bottelx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.ScanHistory;

import java.util.UUID;

public interface ScanHistoryRepository
        extends JpaRepository<ScanHistory, UUID> {
}