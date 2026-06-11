package com.bottelx.dto;


public class CounterfeitAlertResponse {

     private String productId;

    private String product;

    private String location;

    private long scans;

    private String risk;

    public CounterfeitAlertResponse() {
    }

    public CounterfeitAlertResponse(
            String productId,
            String product,
            String location,
            long scans,
            String risk
    ) {
        this.productId = productId;
        this.product = product;
        this.location = location;
        this.scans = scans;
        this.risk = risk;
    }

    public String getProductId() {
        return productId;
    }

    public String getProduct() {
        return product;
    }

    public String getLocation() {
        return location;
    }

    public long getScans() {
        return scans;
    }

    public String getRisk() {
        return risk;
    }
}
