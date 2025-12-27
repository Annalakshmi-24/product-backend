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
        dto.setCategoryId(product.getCategoryId());
        dto.setProductName(product.getProductName());
        dto.setIsActive(product.getIsActive());
        dto.setStatus(product.getStatus());
        dto.setCreatedBy(product.getCreatedBy());
        dto.setCurrentStock(product.getCurrentStock());
        dto.setMinStock(product.getMinStock());
        dto.setPurchasePrice(product.getPurchasePrice());
        dto.setModified(product.getModified());
        dto.setModifiedBy(product.getModifiedBy());
        dto.setSalesPrice(product.getSalesPrice());
        dto.setCreated(product.getCreated());
        dto.setOrganizationId(product.getOrganizationId());
   
        dto.setTaxRate(product.getTaxRate());



         dto.setCode(product.getCode());
         dto.setUnit(product.getUnit());


        return dto;
    }

    public static Product toEntity(ProductDto dto) 
    {
        if (dto == null) return null;

        Product product = new Product();

        product.setId(dto.getId());
        product.setSku(dto.getSku());
        product.setTenantId(dto.getTenantId());
        product.setCategoryId(dto.getCategoryId());
        product.setProductName(dto.getProductName());
        product.setIsActive(dto.getIsActive());
        product.setStatus(dto.getStatus());
        product.setCreatedBy(dto.getCreatedBy());
        product.setCurrentStock(dto.getCurrentStock());
        product.setMinStock(dto.getMinStock());
        product.setPurchasePrice(dto.getPurchasePrice());
        product.setModified(dto.getModified());
        product.setModifiedBy(dto.getModifiedBy());
        product.setSalesPrice(dto.getSalesPrice());
        product.setCreated(dto.getCreated());
        product.setOrganizationId(dto.getOrganizationId());
        product.setTaxRate(dto.getTaxRate());
        product.setCode(dto.getCode());
         product.setUnit(dto.getUnit());


        return product;
    }
}
