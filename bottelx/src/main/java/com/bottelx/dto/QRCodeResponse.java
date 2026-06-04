package com.bottelx.dto;


import java.time.LocalDateTime;

public class QRCodeResponse {

    private String id;

    private String qrCode;

    private String qrType;

    private boolean active;

    private String productName;

    private String batchNumber;

    private LocalDateTime createdAt;

    private LocalDateTime lastScannedAt;

    public QRCodeResponse() {
    }

    public QRCodeResponse(
            String id,
            String qrCode,
            String qrType,
            boolean active,
            String productName,
            String batchNumber,
            LocalDateTime createdAt,
            LocalDateTime lastScannedAt
    ) {
        this.id = id;
        this.qrCode = qrCode;
        this.qrType = qrType;
        this.active = active;
        this.productName = productName;
        this.batchNumber = batchNumber;
        this.createdAt = createdAt;
        this.lastScannedAt = lastScannedAt;
    }

    public String getId() {
        return id;
    }

    public String getQrCode() {
        return qrCode;
    }

    public String getQrType() {
        return qrType;
    }

    public boolean isActive() {
        return active;
    }

    public String getProductName() {
        return productName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastScannedAt() {
        return lastScannedAt;
    }
}
