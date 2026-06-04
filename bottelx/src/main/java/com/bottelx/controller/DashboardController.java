package com.bottelx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.BatchResponse;
import com.bottelx.dto.CounterfeitAlertResponse;
import com.bottelx.dto.DashboardStatsResponse;
import com.bottelx.dto.DistributorRiskResponse;
import com.bottelx.dto.ProductFraudResponse;
import com.bottelx.services.DashboardService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStatsResponse stats() {

        return dashboardService.getStats();
    }

    @GetMapping("/counterfeit-alerts")
    public List<CounterfeitAlertResponse> alerts() {

        return dashboardService
                .getCounterfeitAlerts();
    }

    @GetMapping("/product-fraud")
    public List<ProductFraudResponse> productFraud() {

        return dashboardService
                .getProductFraudAnalytics();
    }

    @GetMapping("/recent-batches")
    public List<BatchResponse> recentBatches() {

        return dashboardService
                .getRecentBatches();
    }

    @GetMapping("/distributor-risk")
    public List<DistributorRiskResponse> distributorRisk() {

        return dashboardService
                .getDistributorRisk();
    }
}
