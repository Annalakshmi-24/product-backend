package com.billing.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.billing.dto.ProductPaginationResponse;
import com.billing.models.Product;
import com.billing.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) 
    {
        this.productService = productService;
    }

    // CREATE - POST
    @PostMapping
    public Product createProduct(
            @RequestParam Long tenant_id,
            @RequestParam Long organization_id,
            @RequestBody Product product) 
    {
        return productService.saveProduct(tenant_id, organization_id, product);
    }

    // READ - GET List (with optional filters)
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) Long tenant_id,
            @RequestParam(required = false) Long organization_id) 
    {
        return productService.getProductsByTenantAndOrg(tenant_id, organization_id);
    }

    // READ - GET by ID
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) 
    {
        return productService.getProductById(id);
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public Product updateProduct(
        @PathVariable Long id,
        @RequestBody Product product) 
    {
        return productService.updateProduct(id, product);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) 
    {
        productService.deleteProduct(id);
        return "Product deleted successfully!";
    }

    @GetMapping("/pagination")
    public List<ProductPaginationResponse> getProductsPagination(
            @RequestParam Long tenantId,
            @RequestParam Long organizationId,
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "1") Integer offsetStart,
            @RequestParam(defaultValue = "10") Integer rowsPerPage) 
    {
        return productService.getProductsPagination(tenantId, organizationId, searchText, offsetStart, rowsPerPage);
    }

}
