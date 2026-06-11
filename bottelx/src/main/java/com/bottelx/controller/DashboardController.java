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
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats/{companyId}")
    public DashboardStatsResponse stats(@PathVariable UUID companyId) {

        return dashboardService.getStats(companyId);
    }

    @GetMapping("/counterfeit-alerts/{companyId}")
    public List<CounterfeitAlertResponse> alerts(@PathVariable  UUID companyId) {

        return dashboardService
                .getCounterfeitAlerts(companyId);
    }

    @GetMapping("/product-fraud/{companyId}")
    public List<ProductFraudResponse> productFraud(@PathVariable UUID companyId) {

        return dashboardService
                .getProductFraudAnalytics(companyId);
    }

    @GetMapping("/recent-batches/{companyId}")
    public List<BatchResponse> recentBatches(@PathVariable UUID companyId ) {

        return dashboardService
                .getRecentBatches(companyId);
    }

    @GetMapping("/distributor-risk/{companyId}")
    public List<DistributorRiskResponse> distributorRisk(@PathVariable UUID companyId) {

        return dashboardService
                .getDistributorRisk(companyId);
    }
}
