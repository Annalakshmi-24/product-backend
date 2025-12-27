package com.billing.validation;

import com.billing.models.Product;

public class ProductValidation 
{

    // CREATE VALIDATION
    public static void validateSave(Product product) {

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getSku() == null || product.getSku().trim().isEmpty()) {
            throw new IllegalArgumentException("SKU is required");
        }
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getPurchasePrice() != null && product.getPurchasePrice() < 0) {
            throw new IllegalArgumentException("Purchase price cannot be negative");
        }
        if (product.getSalesPrice() != null && product.getSalesPrice() < 0) {
            throw new IllegalArgumentException("Sales price cannot be negative");
        }
        if (product.getCurrentStock() != null && product.getCurrentStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }

    // UPDATE VALIDATION
    public static void validateUpdate(Long id, Product product) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getPurchasePrice() != null && product.getPurchasePrice() < 0) {
            throw new IllegalArgumentException("Purchase price cannot be negative");
        }
        if (product.getSalesPrice() != null && product.getSalesPrice() < 0) {
            throw new IllegalArgumentException("Sales price cannot be negative");
        }
        if (product.getCurrentStock() != null && product.getCurrentStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }

    // PAGINATION PARAMETER VALIDATION
    public static void validatePagination(String tenantId, String orgId, Integer offset, Integer rows, String sortColumn, String sortOrder) {

        if (tenantId == "") {
            throw new IllegalArgumentException("tenant_id is not must be empty");
        }
        if (orgId =="") {
            throw new IllegalArgumentException("organization_id not must be empty");
        }
        if (offset == null || offset < 1) {
            throw new IllegalArgumentException("offsetStart must be at least 1");
        }
        if (rows == null || rows < 1) {
            throw new IllegalArgumentException("rowsPerPage must be at least 1");
        }
    }
}
