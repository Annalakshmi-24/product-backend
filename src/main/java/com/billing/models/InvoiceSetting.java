package com.billing.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "invoice_setting")
public class InvoiceSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "organization_id)")
    private String organizationId;

    @Column(name = "invoice_prefix")
    private String invoicePrefix;

    @Column(name = "invoice_number")
    private Integer invoiceNumber;

    @Column(name = "payment_terms")
    private Integer paymentTerms;

    @Column(name = "currency_id")
    private String currencyId;

    @Column(name = "notes")
    private String notes;

    @Column(name = "is_show_logo")
    private Boolean isShowLogo;

    @Column(name = "is_auto_invoice")
    private Boolean isAutoInvoice;

   
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime createdAt;

   
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @org.hibernate.annotations.UpdateTimestamp
    private LocalDateTime updatedAt;

    

// getters & setters

public Long getId() { return id; }

public void setId(Long id) {this.id = id; }

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
