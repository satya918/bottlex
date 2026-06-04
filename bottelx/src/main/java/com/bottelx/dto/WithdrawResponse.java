package com.bottelx.dto;

import java.math.BigDecimal;
import java.util.List;

public class WithdrawResponse {

    private BigDecimal walletBalance;

    private String upiId;

    private List<History> history;

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(
            BigDecimal walletBalance
    ) {
        this.walletBalance =
                walletBalance;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(
            String upiId
    ) {
        this.upiId = upiId;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(
            List<History> history
    ) {
        this.history = history;
    }

    // INNER CLASS

    public static class History {

        private BigDecimal amount;

        private String status;

        private String date;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(
                BigDecimal amount
        ) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(
                String status
        ) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(
                String date
        ) {
            this.date = date;
        }
    }
}
