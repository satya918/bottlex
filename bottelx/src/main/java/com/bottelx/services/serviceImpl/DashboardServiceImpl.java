package com.bottelx.services.serviceImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottelx.dto.BatchResponse;
import com.bottelx.dto.CounterfeitAlertResponse;
import com.bottelx.dto.DashboardStatsResponse;
import com.bottelx.dto.DistributorRiskResponse;
import com.bottelx.dto.ProductFraudResponse;
import com.bottelx.entity.Batch;
import com.bottelx.entity.Product;
import com.bottelx.repository.BatchRepository;
import com.bottelx.repository.ProductRepository;
import com.bottelx.repository.QRScanLogRepository;

import com.bottelx.services.DashboardService;

@Service
public class DashboardServiceImpl
        implements DashboardService {

    @Autowired
    private QRScanLogRepository qrScanLogRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public DashboardStatsResponse getStats(UUID companyId) {

        long totalScans = qrScanLogRepository
                .countByCompanyId(companyId);

        long authenticBottles = qrScanLogRepository
                .countByCompany_IdAndScanStatus(
                        companyId,
                        "AUTHENTIC");

        long counterfeitAlerts = qrScanLogRepository
                .countByCompany_IdAndScanStatus(
                        companyId,
                        "COUNTERFEIT");

        long duplicateQr = qrScanLogRepository
                .countByCompany_IdAndScanStatus(
                        companyId,
                        "SAME_USER_DUPLICATE_SCAN");

        return new DashboardStatsResponse(
                totalScans,
                authenticBottles,
                counterfeitAlerts,
                duplicateQr);
    }

    @Override
    public List<CounterfeitAlertResponse> getCounterfeitAlerts(UUID companyId) {
        return qrScanLogRepository.findCounterfeitAlerts(companyId);
    }

    @Override
    public List<ProductFraudResponse> getProductFraudAnalytics(UUID companyId) {

        List<Product> products = productRepository
                .findAllByCompany_IdAndActiveTrue(
                        companyId);

        return products.stream()
                .map(product -> {

                    long totalScans = qrScanLogRepository
                            .countByProductId(
                                    product.getId());

                    long fakeScans = qrScanLogRepository
                            .countFakeScansByProductId(
                                    product.getId());

                    double percentage = totalScans == 0
                            ? 0
                            : (fakeScans * 100.0)
                                    / totalScans;

                    return new ProductFraudResponse(
                            product.getProductName(),
                            String.format(
                                    "%.1f%%",
                                    percentage));
                })
                .toList();
    }

    @Override
    public List<BatchResponse> getRecentBatches(UUID companyId) {
        return batchRepository
                .findTop10ByCompany_IdOrderByCreatedAtDesc(companyId)
                .stream()
                .map(this::mapBatch)
                .collect(Collectors.toList());
    }

    @Override
    public List<DistributorRiskResponse> getDistributorRisk(UUID companyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDistributorRisk'");
    }

    private BatchResponse mapBatch(
            Batch batch) {

        return new BatchResponse(
                batch.getId(),
                batch.getBatchNumber(),
                batch.getQuantity(),
                batch.getRemainingQuantity(),
                batch.getProduct().getId(),
                batch.getProduct()
                        .getProductName(),
                batch.getManufacturingDate(),
                batch.getExpiryDate(),

                batch.getActive());
    }
}
