package com.bottelx.dto;


public class DashboardStatsResponse {

    private long totalScans;

    private long authenticBottles;

    private long counterfeitAlerts;

    private long duplicateQr;

    public DashboardStatsResponse() {
    }

    public DashboardStatsResponse(
            long totalScans,
            long authenticBottles,
            long counterfeitAlerts,
            long duplicateQr
    ) {
        this.totalScans = totalScans;
        this.authenticBottles = authenticBottles;
        this.counterfeitAlerts = counterfeitAlerts;
        this.duplicateQr = duplicateQr;
    }

    public long getTotalScans() {
        return totalScans;
    }

    public void setTotalScans(long totalScans) {
        this.totalScans = totalScans;
    }

    public long getAuthenticBottles() {
        return authenticBottles;
    }

    public void setAuthenticBottles(long authenticBottles) {
        this.authenticBottles = authenticBottles;
    }

    public long getCounterfeitAlerts() {
        return counterfeitAlerts;
    }

    public void setCounterfeitAlerts(long counterfeitAlerts) {
        this.counterfeitAlerts = counterfeitAlerts;
    }

    public long getDuplicateQr() {
        return duplicateQr;
    }

    public void setDuplicateQr(long duplicateQr) {
        this.duplicateQr = duplicateQr;
    }
}
