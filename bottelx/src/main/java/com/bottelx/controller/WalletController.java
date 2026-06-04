package com.bottelx.controller;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bottelx.dto.WithdrawRequest;
import com.bottelx.dto.WithdrawResponse;
import com.bottelx.entity.User;
import com.bottelx.entity.WithdrawTransaction;
import com.bottelx.repository.UserRepository;
import com.bottelx.repository.WithdrawTransactionRepository;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WithdrawTransactionRepository withdrawTransactionRepository;

    // GET WALLET DETAILS

    @GetMapping("/details")
    public ResponseEntity<?> getWalletDetails(
            Authentication authentication) {

        try {

            String username = authentication.getName();

            User user = userRepository
                    .findByUserNameIgnoreCaseOrEmailIgnoreCase(
                            username,
                            username)
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "User not found"));

            WithdrawResponse response = new WithdrawResponse();

            response.setWalletBalance(
                    user.getWalletBalance());

            response.setUpiId(
                    user.getUpiId());

            // HISTORY

            List<WithdrawTransaction> transactions =

                    withdrawTransactionRepository
                            .findTop10ByUserOrderByCreatedAtDesc(
                                    user);

            List<WithdrawResponse.History> historyList = new ArrayList<>();

            DateTimeFormatter formatter =

                    DateTimeFormatter.ofPattern(
                            "dd MMM yyyy");

            for (WithdrawTransaction tx : transactions) {

                WithdrawResponse.History history = new WithdrawResponse.History();

                history.setAmount(
                        tx.getAmount());

                history.setStatus(
                        tx.getStatus());

                history.setDate(

                        tx.getCreatedAt()

                                .atZone(
                                        ZoneId.systemDefault())

                                .format(
                                        formatter));

                historyList.add(
                        history);
            }

            response.setHistory(
                    historyList);

            return ResponseEntity.ok(
                    response);

        } catch (Exception ex) {

            ex.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body("Failed to load wallet");
        }
    }

    // WITHDRAW API

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @RequestBody WithdrawRequest request,

            Authentication authentication) {

        try {

            String username = authentication.getName();

            User user = userRepository
                    .findByUserNameIgnoreCaseOrEmailIgnoreCase(
                            username,
                            username)
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "User not found"));

            // VALIDATION

            if (request.getAmount()
                    .compareTo(BigDecimal.ZERO) <= 0) {

                return ResponseEntity
                        .badRequest()
                        .body("Invalid amount");
            }

            if (user.getWalletBalance()
                    .compareTo(
                            request.getAmount()) < 0) {

                return ResponseEntity
                        .badRequest()
                        .body("Insufficient balance");
            }

            // DEDUCT BALANCE

            user.setWalletBalance(

                    user.getWalletBalance()

                            .subtract(
                                    request.getAmount()));

            user.setUpiId(
                    request.getUpiId());

            userRepository.save(
                    user);

            // SAVE TRANSACTION

            WithdrawTransaction tx = new WithdrawTransaction();

            tx.setUser(user);

            tx.setAmount(
                    request.getAmount());

            tx.setUpiId(
                    request.getUpiId());

            tx.setStatus(
                    "PENDING");

            withdrawTransactionRepository
                    .save(tx);

            return ResponseEntity.ok(
                    "Withdrawal request submitted");

        } catch (Exception ex) {

            ex.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Withdraw failed");
        }
    }
}
