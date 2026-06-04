package com.bottelx.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.RewardTransaction;
import com.bottelx.entity.User;

public interface RewardTransactionRepository
        extends JpaRepository<
        RewardTransaction,
        UUID> {

    List<RewardTransaction>
    findTop10ByUserOrderByCreatedAtDesc(
            User user
    );
}
