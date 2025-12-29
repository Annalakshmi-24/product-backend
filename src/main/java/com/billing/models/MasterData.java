package com.billing.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "master_data")
public class MasterData 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    private String tenantId;
    private String organizationId;
    private String productName;
    private String status;
    private Integer currentStock;
    private Double purchasePrice;
    private Integer active;
    private String createdBy;
    private String modifiedBy;
    private Double salesPrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    private Integer categoryId;
    private Integer taxRate;
    private Integer minimumStock;
    private Integer code;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "entity_type_id", referencedColumnName = "id")
    private EntityType entityType;  // link to entity_type

    private String value;  // actual dropdown value

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getOrganizationId() { return organizationId; }
    public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }

    public Double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(Double purchasePrice) { this.purchasePrice = purchasePrice; }

    public Integer getActive() { return active; }
    public void setActive(Integer active) { this.active = active; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public Double getSalesPrice() { return salesPrice; }
    public void setSalesPrice(Double salesPrice) { this.salesPrice = salesPrice; }

    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }

    public Date getModified() { return modified; }
    public void setModified(Date modified) { this.modified = modified; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Integer getTaxRate() { return taxRate; }
    public void setTaxRate(Integer taxRate) { this.taxRate = taxRate; }

    public Integer getMinimumStock() { return minimumStock; }
    public void setMinimumStock(Integer minimumStock) { this.minimumStock = minimumStock; }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public EntityType getEntityType() { return entityType; }
    public void setEntityType(EntityType entityType) { this.entityType = entityType; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
