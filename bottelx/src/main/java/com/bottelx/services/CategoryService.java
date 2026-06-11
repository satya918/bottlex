package com.bottelx.services;



import java.util.List;
import java.util.UUID;

import com.bottelx.dto.CategoryRequest;
import com.bottelx.dto.CategoryResponse;

public interface CategoryService {

    CategoryResponse createCategory(
            UUID companyId,
            CategoryRequest request
    );

    CategoryResponse updateCategory(
            UUID companyId,
            String id,
            CategoryRequest request
    );

    List<CategoryResponse> getAllCategories(
            UUID companyId
    );

    CategoryResponse getCategoryById(
            UUID companyId,
            String id
    );

    void deleteCategory(
            UUID companyId,
            String id
    );

    void toggleCategoryStatus(
            UUID companyId,
            String id,
            Boolean active
    );
}
