package com.bottelx.dto;


public class VerifyQRResponse {

     private String status;

    private String productName;

    private String batchNumber;

    private String message;

    public VerifyQRResponse() {
    }

    public VerifyQRResponse(
          String status,
            String productName,
            String batchNumber,
            String message
    ) {
       this.status = status;
        this.productName = productName;
        this.batchNumber = batchNumber;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getMessage() {
        return message;
    }
}
