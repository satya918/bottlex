package com.bottelx.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bottelx.entity.User;
import com.bottelx.entity.WithdrawTransaction;

public interface WithdrawTransactionRepository
        extends JpaRepository<
        WithdrawTransaction,
        UUID> {

    List<WithdrawTransaction>
    findTop10ByUserOrderByCreatedAtDesc(
            User user
    );
}
