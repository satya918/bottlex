package com.bottelx.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottelx.dto.CategoryRequest;
import com.bottelx.dto.CategoryResponse;
import com.bottelx.entity.Category;
import com.bottelx.entity.Company;
import com.bottelx.repository.CategoryRepository;
import com.bottelx.repository.CompanyRepository;
import com.bottelx.services.CategoryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl
                implements CategoryService {
        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private CompanyRepository companyRepository;

        @Override
        public CategoryResponse createCategory(
                        UUID companyId,
                        CategoryRequest request) {

                if (categoryRepository.existsByCategoryCodeAndCompany_Id(
                                request.getCategoryCode(),
                                companyId)) {

                        throw new RuntimeException(
                                        "Category code already exists");
                }

                Company company = companyRepository
                                .findById(companyId)
                                .orElseThrow(() -> new RuntimeException("Company not found"));

                Category category = Category.builder()
                                .categoryName(request.getCategoryName())
                                .categoryCode(request.getCategoryCode())
                                .description(request.getDescription())
                                .company(company)
                                .active(true)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();

                categoryRepository.save(category);

                return mapToResponse(category);
        }

        @Override
        public CategoryResponse updateCategory(
                        UUID companyId,
                        String id,
                        CategoryRequest request) {

                Category category = categoryRepository
                                .findByIdAndCompany_Id(id, companyId)
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                category.setCategoryName(request.getCategoryName());
                category.setCategoryCode(request.getCategoryCode());
                category.setDescription(request.getDescription());
                category.setUpdatedAt(LocalDateTime.now());

                categoryRepository.save(category);

                return mapToResponse(category);
        }

        @Override
        public List<CategoryResponse> getAllCategories(
                        UUID companyId) {

                return categoryRepository
                                .findAllByCompany_IdAndActiveTrue(companyId)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        public CategoryResponse getCategoryById(
                        UUID companyId,
                        String id) {

                Category category = categoryRepository
                                .findByIdAndCompany_Id(id, companyId)
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                return mapToResponse(category);
        }

        @Override
        public void deleteCategory(
                        UUID companyId,
                        String id) {

                Category category = categoryRepository
                                .findByIdAndCompany_Id(id, companyId)
                                .orElseThrow(() -> new RuntimeException("Category not found"));
                category.setActive(false);
                categoryRepository.save(category);
        }

        @Override
        public void toggleCategoryStatus(
                        UUID companyId,
                        String id,
                        Boolean active) {

                Category category = categoryRepository
                                .findByIdAndCompany_Id(id, companyId)
                                .orElseThrow(() -> new RuntimeException("Category not found"));

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
