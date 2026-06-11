package com.bottelx.services;



import java.util.List;
import java.util.UUID;

import com.bottelx.dto.BatchResponse;
import com.bottelx.dto.CounterfeitAlertResponse;
import com.bottelx.dto.DashboardStatsResponse;
import com.bottelx.dto.DistributorRiskResponse;
import com.bottelx.dto.ProductFraudResponse;

public interface DashboardService {

    DashboardStatsResponse getStats(UUID companyId);

    List<CounterfeitAlertResponse>
    getCounterfeitAlerts(UUID companyId);

    List<ProductFraudResponse>
    getProductFraudAnalytics(UUID companyId);

    List<BatchResponse>
    getRecentBatches(UUID companyId);

    List<DistributorRiskResponse>
    getDistributorRisk(UUID companyId);
}