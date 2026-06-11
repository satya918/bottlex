package com.bottelx.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bottelx.dto.ProductRequest;
import com.bottelx.dto.ProductResponse;

public interface ProductService {

    ProductResponse create(
        ProductRequest request,
        UUID companyId
    );

    ProductResponse update(
        UUID companyId,
        String id,
        ProductRequest request
    );

    void delete(UUID companyId,String id);

    Page<ProductResponse> getAll(
        UUID companyId,
        String search,
        Pageable pageable
    );

    void toggleStatus(
        UUID companyId,
        String id,
        Boolean active
    );
}
