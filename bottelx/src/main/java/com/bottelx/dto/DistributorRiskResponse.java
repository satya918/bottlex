package com.bottelx.dto;


public class DistributorRiskResponse {

    private String id;

    private String distributorName;

    private long fakeAlerts;

    private String risk;

    public DistributorRiskResponse() {
    }

    public DistributorRiskResponse(
            String id,
            String distributorName,
            long fakeAlerts,
            String risk
    ) {
        this.id = id;
        this.distributorName = distributorName;
        this.fakeAlerts = fakeAlerts;
        this.risk = risk;
    }

    public String getId() {
        return id;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public long getFakeAlerts() {
        return fakeAlerts;
    }

    public String getRisk() {
        return risk;
    }
}
