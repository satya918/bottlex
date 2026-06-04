package com.bottelx.dto;

// ============================================
// BatchResponse.java
// ============================================


import java.time.LocalDate;

public class BatchResponse {

    private String id;

    private String batchNumber;

    private Integer quantity;

    private Integer remainingQuantity;

    private String productId;

    private String productName;

    private LocalDate manufacturingDate;

    private LocalDate expiryDate;

    private Boolean active;


   

   

    // =========================================
    // GETTERS & SETTERS
    // =========================================

    public BatchResponse(String id, String batchNumber, Integer quantity, Integer remainingQuantity, String productId,
            String productName, LocalDate manufacturingDate, LocalDate expiryDate, Boolean active) {
        this.id = id;
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.remainingQuantity = remainingQuantity;
        this.productId = productId;
        this.productName = productName;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
