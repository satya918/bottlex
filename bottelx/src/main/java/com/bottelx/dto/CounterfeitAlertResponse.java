package com.bottelx.dto;


public class CounterfeitAlertResponse {

    private String id;

    private String product;

    private String location;

    private int scans;

    private String risk;

    public CounterfeitAlertResponse() {
    }

    public CounterfeitAlertResponse(
            String id,
            String product,
            String location,
            int scans,
            String risk
    ) {
        this.id = id;
        this.product = product;
        this.location = location;
        this.scans = scans;
        this.risk = risk;
    }

    public String getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public String getLocation() {
        return location;
    }

    public int getScans() {
        return scans;
    }

    public String getRisk() {
        return risk;
    }
}
