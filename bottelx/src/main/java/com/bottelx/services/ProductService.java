package com.bottelx.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bottelx.dto.ProductRequest;
import com.bottelx.dto.ProductResponse;

public interface ProductService {

    ProductResponse create(
        ProductRequest request
    );

    ProductResponse update(
        String id,
        ProductRequest request
    );

    void delete(String id);

    Page<ProductResponse> getAll(
        String search,
        Pageable pageable
    );

    void toggleStatus(
        String id,
        Boolean active
    );
}
