package com.billing.service;

import java.util.List;
import com.billing.models.Product;
import com.billing.dto.ProductPaginationResponse;

public interface ProductService {

    Product saveProduct(Long tenant_id, Long organization_id, Product product);

    Product updateProduct(Long id, Product product);   

    Product getProductById(Long id);

    List<Product> getProductsByTenantAndOrg(Long tenant_id, Long organization_id);

    void deleteProduct(Long id);
    
    List<ProductPaginationResponse> getProductsPagination(Long tenantId, Long organizationId, String searchText, Integer offsetStart, Integer rowsPerPage);
}
