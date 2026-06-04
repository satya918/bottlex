package com.bottelx.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottelx.dto.CategoryRequest;
import com.bottelx.dto.CategoryResponse;
import com.bottelx.entity.Category;
import com.bottelx.repository.CategoryRepository;
import com.bottelx.services.CategoryService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl
                implements CategoryService {
        @Autowired
        private CategoryRepository categoryRepository;

        public CategoryServiceImpl(
                        CategoryRepository categoryRepository) {
                this.categoryRepository = categoryRepository;
        }

        @Override
        public CategoryResponse createCategory(
                        CategoryRequest request) {

                if (categoryRepository.existsByCategoryCode(
                                request.getCategoryCode())) {
                        throw new RuntimeException(
                                        "Category code already exists");
                }

                Category category = Category.builder()
                                .categoryName(
                                                request.getCategoryName())
                                .categoryCode(
                                                request.getCategoryCode())
                                .description(
                                                request.getDescription())
                                .active(true)
                                .createdAt(
                                                LocalDateTime.now())
                                .updatedAt(
                                                LocalDateTime.now())
                                .build();

                categoryRepository.save(category);

                return mapToResponse(category);
        }

        @Override
        public CategoryResponse updateCategory(
                        String id,
                        CategoryRequest request) {

                Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Category not found"));

                category.setCategoryName(
                                request.getCategoryName());

                category.setCategoryCode(
                                request.getCategoryCode());

                category.setDescription(
                                request.getDescription());

                category.setUpdatedAt(
                                LocalDateTime.now());

                categoryRepository.save(category);

                return mapToResponse(category);
        }

        @Override
        public List<CategoryResponse> getAllCategories() {

                return categoryRepository.findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public CategoryResponse getCategoryById(
                        String id) {

                Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Category not found"));

                return mapToResponse(category);
        }

        @Override
        public void deleteCategory(
                        String id) {

                categoryRepository.deleteById(id);
        }

        @Override
        public void toggleCategoryStatus(
                        String id,
                        Boolean active) {

                Category category = categoryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Category not found"));

                category.setActive(active);

                categoryRepository.save(category);
        }

        // =========================================
        // MAPPER
        // =========================================

        private CategoryResponse mapToResponse(
                        Category category) {

                return CategoryResponse.builder()
                                .id(category.getId())
                                .categoryName(
                                                category.getCategoryName())
                                .categoryCode(
                                                category.getCategoryCode())
                                .description(
                                                category.getDescription())
                                .active(
                                                category.getActive())
                                .build();
        }
}
