package com.bottelx.services;

import com.bottelx.dto.QrScanRequest;
import com.bottelx.entity.Bottle;
import com.bottelx.entity.RewardTransaction;
import com.bottelx.entity.ScanHistory;
import com.bottelx.entity.User;
import com.bottelx.repository.BottleRepository;
import com.bottelx.repository.RewardTransactionRepository;
import com.bottelx.repository.ScanHistoryRepository;
import com.bottelx.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ScanService {
        @Autowired
        private BottleRepository bottleRepository;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ScanHistoryRepository scanHistoryRepository;
        @Autowired
        private RewardTransactionRepository rewardTransactionRepository;

        public String verifyQr(
                        QrScanRequest request, Authentication authentication){

                Bottle bottle = bottleRepository
                                .findByQrCode(
                                                request.getQrCode())
                                .orElse(null);

                if (bottle == null) {
                        return "Invalid Bottle";
                }

                if (bottle.isScanned()) {
                        return "Bottle Already Scanned";
                }

                User user = userRepository
                                .findByUserNameIgnoreCaseOrEmailIgnoreCase(
                                                authentication.getName(),
                                                authentication.getName())
                                .orElse(null);

                bottle.setScanned(true);

                bottleRepository.save(bottle);

                BigDecimal currentBalance = user.getWalletBalance();

                user.setWalletBalance(
                                currentBalance.add(
                                                bottle.getRewardAmount()));

                userRepository.save(user);

                ScanHistory history = new ScanHistory();

                history.setBottle(bottle);

                history.setUser(user);

                history.setScannedAt(
                                LocalDateTime.now());

                scanHistoryRepository.save(history);

                RewardTransaction transaction = new RewardTransaction();
                transaction.setUser(user);
                transaction.setAmount(bottle.getRewardAmount());        
                transaction.setDescription(
                                "Reward for scanning bottle: " + bottle.getProductName());
               
                 rewardTransactionRepository.save(transaction);

                return "Bottle Verified. Reward Added : ₹"
                                + bottle.getRewardAmount();
        }
}
