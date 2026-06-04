package com.bottelx.dto;

import java.math.BigDecimal;

public class WithdrawRequest {

    private BigDecimal amount;

    private String upiId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(
            BigDecimal amount
    ) {
        this.amount = amount;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(
            String upiId
    ) {
        this.upiId = upiId;
    }
}
