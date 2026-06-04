package com.bottelx.dto;


public class VerifyQRResponse {

    private boolean genuine;

    private boolean suspicious;

    private String productName;

    private String batchNumber;

    private String message;

    public VerifyQRResponse() {
    }

    public VerifyQRResponse(
            boolean genuine,
            boolean suspicious,
            String productName,
            String batchNumber,
            String message
    ) {
        this.genuine = genuine;
        this.suspicious = suspicious;
        this.productName = productName;
        this.batchNumber = batchNumber;
        this.message = message;
    }

    public boolean isGenuine() {
        return genuine;
    }

    public boolean isSuspicious() {
        return suspicious;
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
