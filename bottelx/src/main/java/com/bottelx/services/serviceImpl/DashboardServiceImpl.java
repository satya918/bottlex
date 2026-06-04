package com.bottelx.services.serviceImpl;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

import com.bottelx.dto.BatchResponse;
import com.bottelx.dto.CounterfeitAlertResponse;
import com.bottelx.dto.DashboardStatsResponse;
import com.bottelx.dto.DistributorRiskResponse;
import com.bottelx.dto.ProductFraudResponse;
// import com.bottelx.dto.BatchResponse;
// import com.bottelx.dto.CounterfeitAlertResponse;
// import com.bottelx.dto.DashboardStatsResponse;
// import com.bottelx.dto.DistributorRiskResponse;
// import com.bottelx.dto.ProductFraudResponse;
// import com.bottelx.entity.Batch;
// import com.bottelx.repository.BatchRepository;
// import com.bottelx.repository.QRScanLogRepository;
 import com.bottelx.services.DashboardService;

// import java.util.List;
// import java.util.stream.Collectors;

 @Service
 public class DashboardServiceImpl
                 implements DashboardService {

        @Override
                 public DashboardStatsResponse getStats() {
                 // TODO Auto-generated method stub
                 throw new UnsupportedOperationException("Unimplemented method 'getStats'");
                 }

                 @Override
                 public List<CounterfeitAlertResponse> getCounterfeitAlerts() {
                 // TODO Auto-generated method stub
                 throw new UnsupportedOperationException("Unimplemented method 'getCounterfeitAlerts'");
                 }

                 @Override
                 public List<ProductFraudResponse> getProductFraudAnalytics() {
                 // TODO Auto-generated method stub
                 throw new UnsupportedOperationException("Unimplemented method 'getProductFraudAnalytics'");
                 }

                 @Override
                 public List<BatchResponse> getRecentBatches() {
                 // TODO Auto-generated method stub
                 throw new UnsupportedOperationException("Unimplemented method 'getRecentBatches'");
                 }

                 @Override
                 public List<DistributorRiskResponse> getDistributorRisk() {
                 // TODO Auto-generated method stub
                 throw new UnsupportedOperationException("Unimplemented method 'getDistributorRisk'");
                 }
//         @Autowired
//         private QRScanLogRepository qrScanLogRepository;
//         @Autowired
//         private BatchRepository batchRepository;

//         @Override
//         public DashboardStatsResponse getStats() {

//                 long totalScans = qrScanLogRepository.count();

//                 long authentic = qrScanLogRepository
//                                 .countByScanStatus(
//                                                 "GENUINE");

//                 long counterfeit = qrScanLogRepository
//                                 .countByScanStatus(
//                                                 "FAKE");

//                 long duplicate = qrScanLogRepository
//                                 .countByScanStatus(
//                                                 "DUPLICATE");

//                 return new DashboardStatsResponse(
//                                 totalScans,
//                                 authentic,
//                                 counterfeit,
//                                 duplicate);
//         }

//         @Override
//         public List<CounterfeitAlertResponse> getCounterfeitAlerts() {

//                 return qrScanLogRepository
//                                 .findTop10ByOrderByCreatedAtDesc()
//                                 .stream()
//                                 .map(scan -> new CounterfeitAlertResponse(
//                                                 scan.getId(),
//                                                 scan.getQrCode(),
//                                                 scan.getIpAddress(),
//                                                 scan.getScanStatus(),
                                                                
//                                                                ))
                                                
//                                 .collect(Collectors.toList());
//         }

//         @Override
//         public List<ProductFraudResponse> getProductFraudAnalytics() {

//                 return List.of(

//                                 new ProductFraudResponse(
//                                                 "Premium Whiskey",
//                                                 "14%"),

//                                 new ProductFraudResponse(
//                                                 "Beer Can 500ml",
//                                                 "2%"),

//                                 new ProductFraudResponse(
//                                                 "Vodka Elite",
//                                                 "21%"));
//         }

//         @Override
//         public List<BatchResponse> getRecentBatches() {

//                 return batchRepository
//                                 .findTop10ByOrderByCreatedAtDesc()
//                                 .stream()
//                                 .map(this::mapBatch)
//                                 .collect(Collectors.toList());
//         }

//         @Override
//         public List<DistributorRiskResponse> getDistributorRisk() {

//                 return List.of(

//                                 new DistributorRiskResponse(
//                                                 "1",
//                                                 "XYZ Beverages",
//                                                 3482,
//                                                 "HIGH"),

//                                 new DistributorRiskResponse(
//                                                 "2",
//                                                 "South India Distributors",
//                                                 842,
//                                                 "MEDIUM"));
//         }

//         private BatchResponse mapBatch(
//                         Batch batch) {

//                 return new BatchResponse(

//                                 batch.getId(),

//                                 batch.getBatchNumber(),
//                                 batch.getQuantity(),
//                                 batch.getRemainingQuantity(),

//                                 batch.getProduct()
//                                                 .getId(),

//                                 batch.getProduct()
//                                                 .getProductName(),

//                                 batch.getManufacturingDate(),

//                                 batch.getExpiryDate(),

//                                 batch.getActive());
//         }
 }
