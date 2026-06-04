package com.bottelx.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bottelx.dto.ProductRequest;
import com.bottelx.dto.ProductResponse;
import com.bottelx.entity.Category;
import com.bottelx.entity.Product;
import com.bottelx.repository.CategoryRepository;
import com.bottelx.repository.ProductRepository;
import com.bottelx.services.ProductService;

@Service
public class ProductServiceImpl
        implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductResponse create(
            ProductRequest request) {

        Category category = categoryRepository.findById(
                request.getCategoryId()).orElseThrow();

        Product product = Product.builder()
                .productName(request.getProductName())
                .productCode(request.getProductCode())
                .sku(request.getSku())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .manufacturer(request.getManufacturer())
                .category(category)
                .active(true)
                .build();

        return map(
                productRepository.save(product));
    }

    @Override
    public ProductResponse update(
            String id,
            ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow();

        Category category = categoryRepository.findById(
                request.getCategoryId()).orElseThrow();

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

        product.setManufacturer(
                request.getManufacturer());

        product.setCategory(category);

        return map(
                productRepository.save(product));
    }

    @Override
    public void delete(String id) {

        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductResponse> getAll(
            String search,
            Pageable pageable) {

        return productRepository
                .findByProductNameContainingIgnoreCase(
                        search,
                        pageable)
                .map(this::map);
    }

    @Override
    public void toggleStatus(
            String id,
            Boolean active) {

        Product product = productRepository.findById(id)
                .orElseThrow();

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
                .manufacturer(product.getManufacturer())
                .categoryName(
                        product.getCategory()
                                .getCategoryName())
                .build();
    }
}
