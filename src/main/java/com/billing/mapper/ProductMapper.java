package com.billing.mapper;

import com.billing.dto.ProductDto;
import com.billing.models.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        ProductDto dto = new ProductDto();

        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setTenant_id(product.getTenant_id());
        dto.setProduct_name(product.getProduct_name());
        dto.setCategory(product.getCategory());
        dto.setIs_active(product.getIs_active());
        dto.setStatus(product.getStatus());
        dto.setCreated_by(product.getCreated_by());
        dto.setStock(product.getStock());
        dto.setPurchase_price(product.getPurchase_price());
        dto.setModified(product.getModified());
        dto.setModified_by(product.getModified_by());
        dto.setSales_price(product.getSales_price());
        dto.setCreated(product.getCreated());
        dto.setOrganization_id(product.getOrganization_id());

        return dto;
    }

    public static Product toEntity(ProductDto dto) {
        if (dto == null) return null;

        Product product = new Product();

        product.setId(dto.getId());
        product.setSku(dto.getSku());
        product.setTenant_id(dto.getTenant_id());
        product.setProduct_name(dto.getProduct_name());
        product.setCategory(dto.getCategory());
        product.setIs_active(dto.getIs_active());
        product.setStatus(dto.getStatus());
        product.setCreated_by(dto.getCreated_by());
        product.setStock(dto.getStock());
        product.setPurchase_price(dto.getPurchase_price());
        product.setModified(dto.getModified());
        product.setModified_by(dto.getModified_by());
        product.setSales_price(dto.getSales_price());
        product.setCreated(dto.getCreated());
       product.setOrganization_id(dto.getOrganization_id());

        return product;
    }
}
