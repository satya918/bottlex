package com.bottelx.services;



import java.util.List;

import com.bottelx.dto.BatchResponse;
import com.bottelx.dto.CounterfeitAlertResponse;
import com.bottelx.dto.DashboardStatsResponse;
import com.bottelx.dto.DistributorRiskResponse;
import com.bottelx.dto.ProductFraudResponse;

public interface DashboardService {

    DashboardStatsResponse getStats();

    List<CounterfeitAlertResponse>
    getCounterfeitAlerts();

    List<ProductFraudResponse>
    getProductFraudAnalytics();

    List<BatchResponse>
    getRecentBatches();

    List<DistributorRiskResponse>
    getDistributorRisk();
}