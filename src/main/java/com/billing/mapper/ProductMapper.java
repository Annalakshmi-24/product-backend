package com.billing.mapper;

import com.billing.dto.ProductDto;
import com.billing.models.Product;

public class ProductMapper 
{

    public static ProductDto toDto(Product product) 
    {
        if (product == null) return null;

        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setTenantId(product.getTenantId());
        dto.setProductName(product.getProductName());
        dto.setCategory(product.getCategory());
        dto.setIsActive(product.getIsActive());
        dto.setStatus(product.getStatus());
        dto.setCreatedBy(product.getCreatedBy());
        dto.setStock(product.getStock());
        dto.setPurchasePrice(product.getPurchasePrice());
        dto.setModified(product.getModified());
        dto.setModifiedBy(product.getModifiedBy());
        dto.setSalesPrice(product.getSalesPrice());
        dto.setCreated(product.getCreated());
        dto.setOrganizationId(product.getOrganizationId());

        return dto;
    }

    public static Product toEntity(ProductDto dto) 
    {
        if (dto == null) return null;

        Product product = new Product();

        product.setId(dto.getId());
        product.setSku(dto.getSku());
        product.setTenantId(dto.getTenantId());
        product.setProductName(dto.getProductName());
        product.setCategory(dto.getCategory());
        product.setIsActive(dto.getIsActive());
        product.setStatus(dto.getStatus());
        product.setCreatedBy(dto.getCreatedBy());
        product.setStock(dto.getStock());
        product.setPurchasePrice(dto.getPurchasePrice());
        product.setModified(dto.getModified());
        product.setModifiedBy(dto.getModifiedBy());
        product.setSalesPrice(dto.getSalesPrice());
        product.setCreated(dto.getCreated());
       product.setOrganizationId(dto.getOrganizationId());

        return product;
    }
}
