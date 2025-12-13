package com.billing.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.billing.dto.ProductPaginationResponse;
import com.billing.exception.ProductNotFoundException;
import com.billing.models.Product;
import com.billing.repository.ProductRepository;
import com.billing.validation_interface.ProductValidation;

@Service
public class ProductService 
{

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//CREATE PRODUCT 
    @Transactional
    public Product saveProduct(Product product) {

    // CALL VALIDATION
        ProductValidation.validateSave(product);

        if (product.getCreated() == null) {
            product.setCreated(LocalDateTime.now());
        }

        product.setModified(LocalDateTime.now());

        if (product.getIsActive() == null) {
            product.setIsActive(1);
        }

        return productRepository.save(product);
    }

// UPDATE PRODUCT 
    public Product updateProduct(Long id, Product product) {

        // CALL VALIDATION
        ProductValidation.validateUpdate(id, product);

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setId(existing.getId());
        product.setTenantId(existing.getTenantId());
        product.setOrganizationId(existing.getOrganizationId());
        product.setCreated(existing.getCreated());
        product.setModified(LocalDateTime.now());

        if (product.getIsActive() == null) {
            product.setIsActive(existing.getIsActive());
        }

        if (product.getSku() == null || product.getSku().trim().isEmpty()) {
            product.setSku(existing.getSku());
        }
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            product.setProductName(existing.getProductName());
        }
        if (product.getCategory() == null) {
            product.setCategory(existing.getCategory());
        }
        if (product.getPurchasePrice() == null) {
            product.setPurchasePrice(existing.getPurchasePrice());
        }
        if (product.getSalesPrice() == null) {
            product.setSalesPrice(existing.getSalesPrice());
        }
        if (product.getStock() == null) {
            product.setStock(existing.getStock());
        }
        if (product.getStatus() == null) {
            product.setStatus(existing.getStatus());
        }

        return productRepository.save(product);
    }

//GET PRODUCT BY ID 
    public Product getProductById(String tenantId, String organizationId, Long id) {

        Product product = productRepository.findByTenantIdAndOrganizationIdAndId(tenantId, organizationId, id);

        if (product == null) {
            throw new RuntimeException("Product not found for given tenant, organization, and id");
        }

        return product;
    }


//DELETE 
@Transactional
public void deleteById(String tenantId, String organizationId, Long id) {

    Product product = productRepository
            .findByTenantIdAndOrganizationIdAndIdAndIsActive(
                    tenantId,
                    organizationId,
                    id,
                    1  
            );

    if (product == null) {
        throw new RuntimeException(
                "Active product not found with id: " + id
        );
    }

    product.setIsActive(0); 
    product.setModified(LocalDateTime.now());

    productRepository.save(product);
}

//GetAllProductList
    public List<ProductPaginationResponse> getAllProductList(String tenantId, String organizationId,
                                                                 String searchText, Integer offsetStart,
                                                                 Integer rowsPerPage) {

    // CALL VALIDATION
        ProductValidation.validatePagination(tenantId, organizationId, offsetStart, rowsPerPage);

        if (searchText == null) {
            searchText = "";
        }

        List<Object[]> results = productRepository.getAllProductList(
                tenantId, organizationId, searchText, offsetStart, rowsPerPage
        );

        return results.stream().map(row -> {
            ProductPaginationResponse response = new ProductPaginationResponse();
            response.setId(row[0] != null ? ((Number) row[0]).longValue() : null);
            response.setSku(row[1] != null ? (String) row[1] : null);
            response.setCategory(row[2] != null ? (String) row[2] : null);
            response.setSalePrice(row[3] != null ? ((Number) row[3]).doubleValue() : null);
            response.setStock(row[4] != null ? ((Number) row[4]).intValue() : null);
            response.setStatus(row[5] != null ? String.valueOf(row[5]) : null);

            response.setCreated(row[6] != null ? ((java.sql.Timestamp) row[6]).toLocalDateTime() : null);
            response.setCreatedBy(row[7] != null ? (String) row[7] : null);
            response.setModified(row[8] != null ? ((java.sql.Timestamp) row[8]).toLocalDateTime() : null);
            response.setModifiedBy(row[9] != null ? (String) row[9] : null);
            response.setTotalRowsCount(row[10] != null ? ((Number) row[10]).longValue() : null);

            return response;
        }).collect(Collectors.toList());
    }
}
