package com.bottelx.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "qr_scan_logs")
public class QRScanLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String qrCode;

    private String scanStatus;

    private boolean suspicious;

    private String ipAddress;

    private LocalDateTime scannedAt;

    private LocalDateTime createdAt;



    @ManyToOne
    @JoinColumn(name = "qr_id")
    private QRCode qr;

    public QRScanLog() {
    }

    @PrePersist
    public void prePersist() {
        this.scannedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public QRCode getQr() {
        return qr;
    }

    public void setQr(QRCode qr) {
        this.qr = qr;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    
}