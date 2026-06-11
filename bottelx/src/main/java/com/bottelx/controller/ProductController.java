package com.bottelx.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bottelx.dto.ProductRequest;
import com.bottelx.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
        @Autowired
        private ProductService productService;

        @PostMapping("/{companyId}")
        public ResponseEntity<?> create(
                        @RequestBody ProductRequest request,
                        @PathVariable UUID companyId) {

                return ResponseEntity.ok(
                                productService.create(request, companyId));
        }

        @PutMapping("/{companyId}/{id}")
        public ResponseEntity<?> update(
                        @PathVariable UUID companyId,
                        @PathVariable String id,
                        @RequestBody ProductRequest request) {

                return ResponseEntity.ok(
                                productService.update(companyId, id, request));
        }

        @DeleteMapping("/{companyId}/{id}")
        public ResponseEntity<?> delete(
                        @PathVariable UUID companyId,
                        @PathVariable String id) {

                productService.delete(companyId, id);

                return ResponseEntity.ok().build();
        }

        @GetMapping("/{companyId}")
        public ResponseEntity<?> getAll(
                        @PathVariable UUID companyId,
                        @RequestParam(defaultValue = "") String search,

                        Pageable pageable) {

                return ResponseEntity.ok(
                                productService.getAll(
                                                companyId,
                                                search,
                                                pageable));
        }

        @PatchMapping("/{companyId}/{id}/status")
        public ResponseEntity<?> toggleStatus(
                        @PathVariable UUID companyId,
                        @PathVariable String id,
                        @RequestParam Boolean active) {

                productService.toggleStatus(
                                companyId,
                                id,
                                active);

                return ResponseEntity.ok().build();
        }
}
