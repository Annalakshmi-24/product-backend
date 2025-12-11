package com.billing.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_table")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;
    
    @Column(name = "tenant_id")
    private Long tenant_id;
    
    @Column(name = "organization_id")
    private Long organization_id;

    @Column(name = "product_name")
    private String product_name;
    
    private String category;

    private Integer stock;
    
    @Column(name = "purchase_price")
    private Double purchase_price;
    
    @Column(name = "sales_price")
    private Double sales_price;
    
    @Column(name = "is_active")
    private Integer is_active;
    
    private String status;

    @Column(name = "created_by")
    private String created_by;
    
    @Column(name = "modified_by")
    private String modified_by;

    private LocalDateTime created;
    private LocalDateTime modified;

   

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Long getTenant_id() { return tenant_id; }
    public void setTenant_id(Long tenant_id) { this.tenant_id = tenant_id; }

    public Long getOrganization_id() { return organization_id; }
    public void setOrganization_id(Long organization_id) { this.organization_id = organization_id; }

    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Double getPurchase_price() { return purchase_price; }
    public void setPurchase_price(Double purchase_price) { this.purchase_price = purchase_price; }

    public Double getSales_price() { return sales_price; }
    public void setSales_price(Double sales_price) { this.sales_price = sales_price; }

    public Integer getIs_active() { return is_active; }
    public void setIs_active(Integer is_active) { this.is_active = is_active; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreated_by() { return created_by; }
    public void setCreated_by(String created_by) { this.created_by = created_by; }

    public String getModified_by() { return modified_by; }
    public void setModified_by(String modified_by) { this.modified_by = modified_by; }

    public LocalDateTime getCreated() { return created; }
    public void setCreated(LocalDateTime created) { this.created = created; }

    public LocalDateTime getModified() { return modified; }
    public void setModified(LocalDateTime modified) { this.modified = modified; }
}
