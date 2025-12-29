package com.billing.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billing.dto.ProductIdNameDTO;
import com.billing.dto.ProductPaginationResponse;
import com.billing.exception.ProductCustomExceptions;
import com.billing.exception.ProductNotFoundException;
import com.billing.interfaces.ProductService;
import com.billing.models.Product;
import com.billing.repository.ProductRepository;
import com.billing.validation.ProductValidation;

@Service
public class ProductServiceImpl implements ProductService 
{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) 
    {
        this.productRepository = productRepository;
    }

    // CREATE
    @Override
    @Transactional
    public Product saveProduct(Product product) 
    {

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

    // UPDATE
    @Override
    public Product updateProduct(Long id, String tenantId, String organizationId, Product product) 
    {

        ProductValidation.validateUpdate(id, product);

        if (tenantId == null || organizationId == null) 
        {
            throw new IllegalArgumentException("TenantId and OrganizationId must not be null");
        }

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (!tenantId.equals(existing.getTenantId())
                || !organizationId.equals(existing.getOrganizationId())) 
            {
            throw new ProductNotFoundException(
                    "Product does not belong to given tenant or organization"
            );
        }

        product.setId(existing.getId());
        product.setTenantId(existing.getTenantId());
        product.setOrganizationId(existing.getOrganizationId());
        product.setCreated(existing.getCreated());
        product.setModified(LocalDateTime.now());

        if (product.getIsActive() == null) product.setIsActive(existing.getIsActive());
        if (product.getSku() == null || product.getSku().trim().isEmpty()) product.setSku(existing.getSku());
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) product.setProductName(existing.getProductName());
        if (product.getCategoryId() == null) product.setCategoryId(existing.getCategoryId());
        if (product.getPurchasePrice() == null) product.setPurchasePrice(existing.getPurchasePrice());
        if (product.getSalesPrice() == null) product.setSalesPrice(existing.getSalesPrice());
        if (product.getCurrentStock() == null) product.setCurrentStock(existing.getCurrentStock());
        if (product.getMinStock() == null) product.setMinStock(existing.getMinStock());
        if (product.getStatus() == null) product.setStatus(existing.getStatus());
        if (product.getCreatedBy() == null) product.setCreatedBy(existing.getCreatedBy());
        if (product.getModifiedBy() == null) product.setModifiedBy(existing.getModifiedBy());
        if (product.getCode() == null) product.setCode(existing.getCode());
        if (product.getUnit() == null) product.setUnit(existing.getUnit());
        if (product.getTaxRate() == null) product.setTaxRate(existing.getTaxRate());

        return productRepository.save(product);
    }

    // GET BY ID
    @Override
    public Product getProductById(String tenantId, String organizationId, Long id) {

        Product product = productRepository
                .findByTenantIdAndOrganizationIdAndId(tenantId, organizationId, id);

        if (product == null) {
            throw new RuntimeException("Product not found for given tenant, organization, and id");
        }

        return product;
    }

    // GET ALL ACTIVE
    @Override
    public List<ProductIdNameDTO> getAllProducts(String tenantId, String organizationId) {

        List<Product> products =
                productRepository.findByTenantIdAndOrganizationIdAndIsActive(
                        tenantId, organizationId, 1
                );

        if (products.isEmpty()) {
            throw new ProductCustomExceptions.ResourceNotFoundException(
                    "No active products found for given tenantId and organizationId"
            );
        }

        return products.stream()
                .map(p -> new ProductIdNameDTO(p.getId(), p.getProductName()))
                .toList();
    }

    // PAGINATION
    @Override
    public List<ProductPaginationResponse> getAllProductList(
            String tenantId,
            String organizationId,
            Integer offsetStart,
            Integer rowsPerPage,
            String searchText,
            String sortOrder,
            String sortColumn) {

        ProductValidation.validatePagination(
                tenantId, organizationId, offsetStart, rowsPerPage, sortOrder, sortColumn
        );

        if (searchText == null) searchText = "";

        List<Object[]> results = productRepository.getAllProductList(
                tenantId, organizationId, offsetStart, rowsPerPage,
                searchText, sortOrder, sortColumn
        );

        return results.stream().map(row -> {
            ProductPaginationResponse response = new ProductPaginationResponse();

            response.setId(row[0] != null ? ((Number) row[0]).longValue() : null);
            response.setSku(row[1] != null ? row[1].toString() : null);
            response.setCategoryId(row[2] != null ? ((Number) row[2]).intValue() : null);
            response.setProductName(row[3] != null ? row[3].toString() : null);
            response.setSalePrice(row[4] != null ? ((Number) row[4]).doubleValue() : null);
            response.setStock(row[5] != null ? ((Number) row[5]).intValue() : null);
            response.setStatus(row[6] != null ? row[6].toString() : null);
            response.setCreated(row[7] != null ? ((java.sql.Timestamp) row[7]).toLocalDateTime() : null);
            response.setCreatedBy(row[8] != null ? row[8].toString() : null);
            response.setModified(row[9] != null ? ((java.sql.Timestamp) row[9]).toLocalDateTime() : null);
            response.setModifiedBy(row[10] != null ? row[10].toString() : null);
            response.setCode(row[11] != null ? ((Number) row[11]).intValue() : null);
            response.setUnit(row[12] != null ? row[12].toString() : null);
            response.setTaxRate(row[13] != null ? row[13].toString() : null);
            response.setTotalRowsCount(row[14] != null ? ((Number) row[14]).longValue() : null);

            return response;
        }).collect(Collectors.toList());
    }

    // DELETE (SOFT DELETE)
    @Override
    @Transactional
    public void deleteById(String tenantId, String organizationId, Long id) {

        Product product = productRepository
                .findByTenantIdAndOrganizationIdAndIdAndIsActive(
                        tenantId, organizationId, id, 1
                );

        if (product == null) {
            throw new RuntimeException("Active product not found with id: " + id);
        }

        product.setIsActive(0);
        product.setModified(LocalDateTime.now());

        productRepository.save(product);
    }
}
