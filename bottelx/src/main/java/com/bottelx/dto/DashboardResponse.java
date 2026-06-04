package com.bottelx.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {

    private BigDecimal walletBalance;

    private List<ActivityResponse>
            activities;

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(
            BigDecimal walletBalance
    ) {
        this.walletBalance =
                walletBalance;
    }

    public List<ActivityResponse>
    getActivities() {

        return activities;
    }

    public void setActivities(
            List<ActivityResponse>
                    activities
    ) {

        this.activities =
                activities;
    }
}
