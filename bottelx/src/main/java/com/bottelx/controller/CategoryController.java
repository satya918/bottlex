package com.bottelx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.CategoryRequest;
import com.bottelx.dto.CategoryResponse;
import com.bottelx.services.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
        @Autowired
        private CategoryService categoryService;

        // =========================================
        // CREATE CATEGORY
        // =========================================

        @PostMapping("/{companyId}")
        public ResponseEntity<CategoryResponse> createCategory(
                        @PathVariable UUID companyId,
                        @RequestBody CategoryRequest request) {

                return ResponseEntity.ok(
                                categoryService.createCategory(companyId,
                                                request));
        }

        // =========================================
        // UPDATE CATEGORY
        // =========================================

        @PutMapping("/{companyId}/{id}")
        public ResponseEntity<CategoryResponse> updateCategory(
                        @PathVariable UUID companyId,

                        @PathVariable String id,
                        @RequestBody CategoryRequest request) {

                return ResponseEntity.ok(
                                categoryService.updateCategory(companyId,
                                                id,
                                                request));
        }

        // =========================================
        // GET ALL CATEGORIES
        // =========================================

        @GetMapping("/{companyId}")
        public ResponseEntity<List<CategoryResponse>> getAllCategories(
                        @PathVariable UUID companyId) {

                return ResponseEntity.ok(
                                categoryService.getAllCategories(companyId));
        }

        // =========================================
        // GET CATEGORY BY ID
        // =========================================

        @GetMapping("/{companyId}/{id}")
        public ResponseEntity<CategoryResponse> getCategoryById(
                        @PathVariable UUID companyId,
                        @PathVariable String id) {

                return ResponseEntity.ok(
                                categoryService.getCategoryById(companyId, id));
        }

        // =========================================
        // DELETE CATEGORY
        // =========================================

        @DeleteMapping("/{companyId}/{id}")
        public ResponseEntity<String> deleteCategory(
                        @PathVariable UUID companyId,
                        @PathVariable String id) {

                categoryService.deleteCategory(companyId, id);

                return ResponseEntity.ok(
                                "Category deleted successfully");
        }

        // =========================================
        // TOGGLE STATUS
        // =========================================

        @PatchMapping("/{companyId}/{id}/status")
        public ResponseEntity<String> toggleStatus(
                        @PathVariable UUID companyId,
                        @PathVariable String id,
                        @RequestParam Boolean active) {

                categoryService.toggleCategoryStatus(
                                companyId,
                                id,
                                active);

                return ResponseEntity.ok(
                                "Category status updated");
        }
}
