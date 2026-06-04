package com.bottelx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.CategoryRequest;
import com.bottelx.dto.CategoryResponse;
import com.bottelx.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
        @Autowired
        private CategoryService categoryService;

       

        // =========================================
        // CREATE CATEGORY
        // =========================================

        @PostMapping
        public ResponseEntity<CategoryResponse> createCategory(
                        @RequestBody CategoryRequest request) {

                return ResponseEntity.ok(
                                categoryService.createCategory(
                                                request));
        }

        // =========================================
        // UPDATE CATEGORY
        // =========================================

        @PutMapping("/{id}")
        public ResponseEntity<CategoryResponse> updateCategory(
                        @PathVariable String id,
                        @RequestBody CategoryRequest request) {

                return ResponseEntity.ok(
                                categoryService.updateCategory(
                                                id,
                                                request));
        }

        // =========================================
        // GET ALL CATEGORIES
        // =========================================

        @GetMapping
        public ResponseEntity<List<CategoryResponse>> getAllCategories() {

                return ResponseEntity.ok(
                                categoryService.getAllCategories());
        }

        // =========================================
        // GET CATEGORY BY ID
        // =========================================

        @GetMapping("/{id}")
        public ResponseEntity<CategoryResponse> getCategoryById(
                        @PathVariable String id) {

                return ResponseEntity.ok(
                                categoryService.getCategoryById(id));
        }

        // =========================================
        // DELETE CATEGORY
        // =========================================

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteCategory(
                        @PathVariable String id) {

                categoryService.deleteCategory(id);

                return ResponseEntity.ok(
                                "Category deleted successfully");
        }

        // =========================================
        // TOGGLE STATUS
        // =========================================

        @PatchMapping("/{id}/status")
        public ResponseEntity<String> toggleStatus(
                        @PathVariable String id,
                        @RequestParam Boolean active) {

                categoryService.toggleCategoryStatus(
                                id,
                                active);

                return ResponseEntity.ok(
                                "Category status updated");
        }
}
