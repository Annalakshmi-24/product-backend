package com.billing.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billing.dto.ProductPaginationResponse;
import com.billing.exception.ProductNotFoundException;
import com.billing.models.Product;
import com.billing.repository.ProductRepository;
import com.billing.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService 
{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) 
    {
        this.productRepository = productRepository;
    }

    
    @Override
    @Transactional
    public Product saveProduct(Long tenant_id, Long organization_id, Product product) 
    {
        // Validate inputs
        if (tenant_id == null || tenant_id <= 0) {
            throw new IllegalArgumentException("tenant_id must be a positive number");
        }
        if (organization_id == null || organization_id <= 0) {
            throw new IllegalArgumentException("organization_id must be a positive number");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getSku() == null || product.getSku().trim().isEmpty()) {
            throw new IllegalArgumentException("SKU is required");
        }
        if (product.getProduct_name() == null || product.getProduct_name().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getPurchase_price() != null && product.getPurchase_price() < 0) {
            throw new IllegalArgumentException("Purchase price cannot be negative");
        }
        if (product.getSales_price() != null && product.getSales_price() < 0) {
            throw new IllegalArgumentException("Sales price cannot be negative");
        }
        if (product.getStock() != null && product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        
        product.setTenant_id(tenant_id);
        product.setOrganization_id(organization_id);
        
        // Set created timestamp if not already set
        if (product.getCreated() == null) {
            product.setCreated(LocalDateTime.now());
        }
        
        // Set modified timestamp
        product.setModified(LocalDateTime.now());
        
        // Set default is_active if not provided
        if (product.getIs_active() == null) {
            product.setIs_active(1);
        }
        
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) 
    {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        // Validate prices if provided
        if (product.getPurchase_price() != null && product.getPurchase_price() < 0) {
            throw new IllegalArgumentException("Purchase price cannot be negative");
        }
        if (product.getSales_price() != null && product.getSales_price() < 0) {
            throw new IllegalArgumentException("Sales price cannot be negative");
        }
        if (product.getStock() != null && product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        // Preserve tenant_id and organization_id
        product.setId(existing.getId());
        product.setTenant_id(existing.getTenant_id());
        product.setOrganization_id(existing.getOrganization_id());
        
        // Preserve created timestamp
        product.setCreated(existing.getCreated());
        
        // Set modified timestamp
        product.setModified(LocalDateTime.now());
        
        // Preserve is_active if not provided in update
        if (product.getIs_active() == null) {
            product.setIs_active(existing.getIs_active());
        }
        
        // Preserve other fields if not provided
        if (product.getSku() == null || product.getSku().trim().isEmpty()) {
            product.setSku(existing.getSku());
        }
        if (product.getProduct_name() == null || product.getProduct_name().trim().isEmpty()) {
            product.setProduct_name(existing.getProduct_name());
        }
        if (product.getCategory() == null) {
            product.setCategory(existing.getCategory());
        }
        if (product.getPurchase_price() == null) {
            product.setPurchase_price(existing.getPurchase_price());
        }
        if (product.getSales_price() == null) {
            product.setSales_price(existing.getSales_price());
        }
        if (product.getStock() == null) {
            product.setStock(existing.getStock());
        }
        if (product.getStatus() == null) {
            product.setStatus(existing.getStatus());
        }

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) 
    {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> getProductsByTenantAndOrg(Long tenant_id, Long organization_id) 
    {
        // If both are null, return all products
        if (tenant_id == null && organization_id == null) {
            return productRepository.findAll();
        }
        // If both are provided, filter by both
        if (tenant_id != null && organization_id != null) {
            return productRepository.findByTenantIdAndOrganizationId(tenant_id, organization_id);
        }
        // If only tenant_id is provided, filter by tenant_id and is_active = 1
        if (tenant_id != null && organization_id == null) {
            return productRepository.findByTenantIdAndIsActive(tenant_id, 1);
        }
        // If only organization_id is provided, filter by organization_id and is_active = 1
        if (organization_id != null && tenant_id == null) {
            return productRepository.findByOrganizationIdAndIsActive(organization_id, 1);
        }
        
        return productRepository.findAll();
    }

  
    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        // Check if product exists before deleting
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductPaginationResponse> getProductsPagination(Long tenantId, Long organizationId, 
                                                                 String searchText, Integer offsetStart, 
                                                                 Integer rowsPerPage) {
        // Validate inputs
        if (tenantId == null || tenantId <= 0) {
            throw new IllegalArgumentException("tenantId must be a positive number");
        }
        if (organizationId == null || organizationId <= 0) {
            throw new IllegalArgumentException("organizationId must be a positive number");
        }
        if (offsetStart == null || offsetStart < 1) {
            throw new IllegalArgumentException("offsetStart must be at least 1");
        }
        if (rowsPerPage == null || rowsPerPage < 1) {
            throw new IllegalArgumentException("rowsPerPage must be at least 1");
        }
        
        List<Object[]> results = productRepository.getProductsPagination(
            tenantId, organizationId, searchText, offsetStart, rowsPerPage
        );
        
        return results.stream().map(row -> {
            if (row == null || row.length < 11) {
                throw new IllegalStateException("Invalid result from stored procedure");
            }
            ProductPaginationResponse response = new ProductPaginationResponse();
            response.setId(row[0] != null ? ((Number) row[0]).longValue() : null);
            response.setSku(row[1] != null ? (String) row[1] : null);
            response.setCategory(row[2] != null ? (String) row[2] : null);
            response.setSalePrice(row[3] != null ? ((Number) row[3]).doubleValue() : null);
            response.setStock(row[4] != null ? ((Number) row[4]).intValue() : null);
            response.setStatus(row[5] != null ? String.valueOf(row[5]) : null);
            
            // Convert Timestamp to LocalDateTime
            if (row[6] != null) {
                if (row[6] instanceof Timestamp) {
                    response.setCreated(((Timestamp) row[6]).toLocalDateTime());
                } else if (row[6] instanceof LocalDateTime) {
                    response.setCreated((LocalDateTime) row[6]);
                } else {
                    response.setCreated(null);
                }
            } else {
                response.setCreated(null);
            }
            
            response.setCreatedBy(row[7] != null ? (String) row[7] : null);
            
            // Convert Timestamp to LocalDateTime
            if (row[8] != null) {
                if (row[8] instanceof Timestamp) {
                    response.setModified(((Timestamp) row[8]).toLocalDateTime());
                } else if (row[8] instanceof LocalDateTime) {
                    response.setModified((LocalDateTime) row[8]);
                } else {
                    response.setModified(null);
                }
            } else {
                response.setModified(null);
            }
            
            response.setModifiedBy(row[9] != null ? (String) row[9] : null);
            response.setTotalRowsCount(row[10] != null ? ((Number) row[10]).longValue() : null);
            return response;
        }).collect(Collectors.toList());
    }
}
