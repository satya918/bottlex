package com.bottelx.services.serviceImpl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bottelx.dto.ProductRequest;
import com.bottelx.dto.ProductResponse;
import com.bottelx.entity.Category;
import com.bottelx.entity.Company;
import com.bottelx.entity.Product;
import com.bottelx.repository.CategoryRepository;
import com.bottelx.repository.CompanyRepository;
import com.bottelx.repository.ProductRepository;
import com.bottelx.services.ProductService;

@Service
public class ProductServiceImpl
                implements ProductService {
        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private CompanyRepository companyRepository;

        @Override
        public ProductResponse create(
                        ProductRequest request,
                        UUID companyId) {

                Category category = categoryRepository
                                .findByIdAndCompany_Id(
                                                request.getCategoryId(),
                                                companyId)
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                Company company = companyRepository.findById(
                                companyId).orElseThrow(() -> new RuntimeException("Company not found"));

                Product product = Product.builder()
                                .productName(request.getProductName())
                                .productCode(request.getProductCode())
                                .sku(request.getSku())
                                .description(request.getDescription())
                                .price(request.getPrice())
                                .stockQuantity(request.getStockQuantity())
                                .company(company)
                                .category(category)
                                .active(true)
                                .build();

                return map(
                                productRepository.save(product));
        }

        @Override
        public ProductResponse update(
                        UUID companyId,
                        String id,
                        ProductRequest request) {

                Product product = productRepository
                                .findByIdAndCompany_Id(id, companyId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                // Category category = categoryRepository
                // .findByIdAndCompany_Id(
                // request.getCategoryId(),
                // companyId)
                // .orElseThrow(() -> new RuntimeException("Category not found"));
                Company company = companyRepository.findById(
                                companyId).orElseThrow(() -> new RuntimeException("Company not found"));

                product.setProductName(
                                request.getProductName());

                product.setProductCode(
                                request.getProductCode());

                product.setSku(
                                request.getSku());

                product.setDescription(
                                request.getDescription());

                product.setPrice(
                                request.getPrice());

                product.setStockQuantity(
                                request.getStockQuantity());

                product.setCompany(
                                company);

                // product.setCategory(category);

                return map(
                                productRepository.save(product));
        }

        @Override
        public void delete(
                        UUID companyId,
                        String id) {

                Product product = productRepository
                                .findByIdAndCompany_Id(
                                                id,
                                                companyId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                productRepository.delete(product);
        }

        @Override
        public Page<ProductResponse> getAll(
                        UUID companyId,
                        String search,
                        Pageable pageable) {

                return productRepository
                                .findByCompany_IdAndProductNameContainingIgnoreCaseAndActiveTrue(
                                                companyId,
                                                search,
                                                pageable)
                                .map(this::map);
        }

        @Override
        public void toggleStatus(
                        UUID companyId,
                        String id,
                        Boolean active) {

                Product product = productRepository
                                .findByIdAndCompany_Id(
                                                id,
                                                companyId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                product.setActive(active);

                productRepository.save(product);
        }

        private ProductResponse map(
                        Product product) {

                return ProductResponse.builder()
                                .id(product.getId())
                                .productName(product.getProductName())
                                .productCode(product.getProductCode())
                                .sku(product.getSku())
                                .description(product.getDescription())
                                .price(product.getPrice())
                                .stockQuantity(product.getStockQuantity())
                                .active(product.getActive())
                                .categoryName(
                                                product.getCategory()
                                                                .getCategoryName())
                                .build();
        }
}
