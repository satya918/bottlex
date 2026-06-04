package com.bottelx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bottelx.entity.Product;

@Repository
public interface ProductRepository
    extends JpaRepository<Product, String> {

    Page<Product> findByProductNameContainingIgnoreCase(
        String search,
        Pageable pageable
    );
}
