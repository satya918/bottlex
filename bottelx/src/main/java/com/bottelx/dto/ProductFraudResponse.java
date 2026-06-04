package com.bottelx.dto;


public class ProductFraudResponse {

    private String name;

    private String fakePercent;

    public ProductFraudResponse() {
    }

    public ProductFraudResponse(
            String name,
            String fakePercent
    ) {
        this.name = name;
        this.fakePercent = fakePercent;
    }

    public String getName() {
        return name;
    }

    public String getFakePercent() {
        return fakePercent;
    }
}
