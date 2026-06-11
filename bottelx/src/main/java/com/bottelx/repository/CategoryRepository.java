package com.bottelx.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bottelx.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    boolean existsByCategoryCodeAndCompany_Id(
            String categoryCode,
            UUID companyId);

    List<Category> findAllByCompany_IdAndActiveTrue(
            UUID companyId);

    Optional<Category> findByIdAndCompany_Id(
            String id,
            UUID companyId);
}
