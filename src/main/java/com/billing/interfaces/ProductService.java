package com.billing.interfaces;

import java.util.List;

import com.billing.dto.ProductIdNameDTO;
import com.billing.dto.ProductPaginationResponse;
import com.billing.models.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateProduct(Long id, String tenantId, String organizationId, Product product);

    Product getProductById(String tenantId, String organizationId, Long id);

    List<ProductIdNameDTO> getAllProducts(String tenantId, String organizationId);

    List<ProductPaginationResponse> getAllProductList(
            String tenantId,
            String organizationId,
            Integer offsetStart,
            Integer rowsPerPage,
            String searchText,
            String sortOrder,
            String sortColumn
    );

    void deleteById(String tenantId, String organizationId, Long id);
}

