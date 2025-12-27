package com.billing.dto;
import java.time.LocalDateTime;

public class ProductPaginationResponse 
{
    
    private Long id;
    private String sku;
    private Integer categoryId;
    private String productName;
    private Double salePrice;
    private Integer stock;
    private Integer minStock;
    private String status;
    private LocalDateTime created;
    private String createdBy;
    private LocalDateTime modified;
    private String modifiedBy;
    private Integer code;
    private String unit;
    private String taxRate;
    private Long totalRowsCount;

    
    public ProductPaginationResponse() 
    {
    }

    public ProductPaginationResponse(Long id, String sku, Integer categoryId,String productName, Double salePrice, Integer stock, Integer minStock, String status, LocalDateTime created, 
                                   String createdBy, LocalDateTime modified, String modifiedBy, Integer code, String unit, String taxRate,
                                   Long totalRowsCount) 
    {
        this.id = id;
        this.sku = sku;
        this.categoryId = categoryId;
        this.productName= productName;
        this.salePrice = salePrice;
        this.stock = stock;
        this.minStock =  minStock;
        this.status = status;
        this.created = created;
        this.createdBy = createdBy;
        this.modified = modified;
        this.modifiedBy = modifiedBy;
        this.code = code;
        this.unit = unit;
        this.taxRate =  taxRate;
        this.totalRowsCount = totalRowsCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

     public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
        
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
        
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    

    public Long getTotalRowsCount() {
        return totalRowsCount;
    }

    public void setTotalRowsCount(Long totalRowsCount) {
        this.totalRowsCount = totalRowsCount;
    }
}

