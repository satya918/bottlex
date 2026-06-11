package com.bottelx.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bottelx.entity.Product;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, String> {

    Page<Product> findByCompany_IdAndProductNameContainingIgnoreCaseAndActiveTrue(UUID companyId, String search,
            Pageable pageable);

    Optional<Product> findByIdAndCompany_Id(
            String id,
            UUID companyId);

          List<Product> findAllByCompany_IdAndActiveTrue(UUID companyId);
}
