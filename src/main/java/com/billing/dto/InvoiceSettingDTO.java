package com.billing.dto;
import java.time.LocalDateTime;

public class InvoiceSettingDTO
 {

    private Long id;
    private String tenantId;
    private String organizationId;
    private String invoicePrefix;
    private Integer invoiceNumber;
    private Integer paymentTerms;
    private String currencyId;
    private String notes;
    private Boolean isShowLogo;
    private Boolean isAutoInvoice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

// getters & setters

public Long getId() { return id; }

public void setId(Long id) {this.id = id; }

public String getTenantId() {return tenantId; }

public void setTenantId(String tenandId) {this.tenantId = tenantId;}

public String getOrganizationId() {return organizationId; }

public void setOrganzationId(String organizationId) {this.organizationId = organizationId;}

public String getInvoicePrefix() {return invoicePrefix; }

public void setInvoicePrefix(String invoicePrefix) {this.invoicePrefix = invoicePrefix;}

public Integer getInvoiceNumber() {return invoiceNumber;}

public void setInvoiceNumber(Integer invoiceNumber) {this.invoiceNumber = invoiceNumber;}

public Integer getPaymentTerms() {return paymentTerms;}

public void setPaymentTerms(Integer paymentTerms) {this.paymentTerms = paymentTerms;}

public String getCurrencyId() { return currencyId;}

public void setCurrencyId(String currencyId) {this.currencyId = currencyId;}

public String getNotes() {return notes;}

public void setNotes(String notes) {this.notes = notes;}

public Boolean getIsShowLogo() { return isShowLogo;}

public void setIsShowLogo(Boolean isShowLogo) {this.isShowLogo = isShowLogo;}

public Boolean getIsAutoInvoice() {return isAutoInvoice;}

public void setIsAutoInvoice(Boolean isAutoInvoice) { this.isAutoInvoice = isAutoInvoice;}

public LocalDateTime getCreatedAt() { return createdAt;}

public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt;}

public LocalDateTime getUpdatedAt() { return updatedAt;}

public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt;}

}

