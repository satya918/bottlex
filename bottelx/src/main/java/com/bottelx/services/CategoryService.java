package com.bottelx.services;



import java.util.List;

import com.bottelx.dto.CategoryRequest;
import com.bottelx.dto.CategoryResponse;

public interface CategoryService {

    CategoryResponse createCategory(
            CategoryRequest request
    );

    CategoryResponse updateCategory(
            String id,
            CategoryRequest request
    );

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(
            String id
    );

    void deleteCategory(
            String id
    );

    void toggleCategoryStatus(
            String id,
            Boolean active
    );
}
