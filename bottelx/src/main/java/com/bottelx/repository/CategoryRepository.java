package com.bottelx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bottelx.entity.Category;

@Repository
public interface CategoryRepository
    extends JpaRepository<Category, String> {

         boolean existsByCategoryCode(String categoryCode);

    boolean existsByCategoryName(String categoryName);
}
