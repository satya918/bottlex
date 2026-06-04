package com.bottelx.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bottelx.dto.ActivityResponse;
import com.bottelx.dto.DashboardResponse;
import com.bottelx.dto.ProfileUpdateRequest;
import com.bottelx.entity.RewardTransaction;
import com.bottelx.entity.User;
import com.bottelx.repository.RewardTransactionRepository;
import com.bottelx.repository.UserRepository;

import java.time.ZoneId;

import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private RewardTransactionRepository rewardTransactionRepository;

        @GetMapping("/dashboard")
        public ResponseEntity<?> getDashboard(
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

                        DashboardResponse response = new DashboardResponse();

                        // WALLET BALANCE

                        response.setWalletBalance(
                                        user.getWalletBalance());

                        // RECENT ACTIVITIES

                        List<RewardTransaction> transactions =

                                        rewardTransactionRepository
                                                        .findTop10ByUserOrderByCreatedAtDesc(
                                                                        user);

                        List<ActivityResponse> activities = new ArrayList<>();

                        DateTimeFormatter formatter =

                                        DateTimeFormatter.ofPattern(
                                                        "dd MMM yyyy");

                        for (RewardTransaction tx : transactions) {

                                ActivityResponse activity = new ActivityResponse();

                                activity.setTitle(
                                                tx.getDescription());

                                activity.setAmount(
                                                tx.getAmount());

                                activity.setDate(

                                                tx.getCreatedAt()

                                                                .atZone(
                                                                                ZoneId.systemDefault())

                                                                .format(
                                                                                formatter));

                                activities.add(activity);
                        }

                        response.setActivities(
                                        activities);

                        return ResponseEntity.ok(
                                        response);

                } catch (Exception ex) {

                        ex.printStackTrace();

                        return ResponseEntity
                                        .internalServerError()
                                        .body("Failed to load dashboard");
                }
        }

        // GET PROFILE

        @GetMapping("/profile")
        public ResponseEntity<?> getProfile(
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

                        return ResponseEntity.ok(
                                        user);

                } catch (Exception ex) {

                        ex.printStackTrace();

                        return ResponseEntity
                                        .badRequest()
                                        .body("Failed to load profile");
                }
        }

        // UPDATE PROFILE

        @PutMapping("/profile")
        public ResponseEntity<?> updateProfile(
                        @RequestBody ProfileUpdateRequest request,

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

                        // UPDATE FIELDS

                        user.setFirstName(
                                        request.getFirstName());

                        user.setLastName(
                                        request.getLastName());

                        user.setEmail(
                                        request.getEmail());

                        user.setPhone(
                                        request.getPhone());

                        user.setUpiId(
                                        request.getUpiId());

                        user.setProfileImage(
                                        request.getProfileImage());

                        userRepository.save(
                                        user);

                        return ResponseEntity.ok(
                                        user);

                } catch (Exception ex) {

                        ex.printStackTrace();

                        return ResponseEntity
                                        .badRequest()
                                        .body("Failed to update profile");
                }
        }
}
