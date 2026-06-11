package com.bottelx.dto;



public class QRGenerationResponse {

    private String batchId;
    private String batchNumber;
    private int generatedCount;
    private String message;

    public QRGenerationResponse() {}

    public QRGenerationResponse(
            String batchId,
            String batchNumber,
            int generatedCount,
            String message) {
        this.batchId = batchId;
        this.batchNumber = batchNumber;
        this.generatedCount = generatedCount;
        this.message = message;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getGeneratedCount() {
        return generatedCount;
    }

    public void setGeneratedCount(int generatedCount) {
        this.generatedCount = generatedCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // getters setters
}