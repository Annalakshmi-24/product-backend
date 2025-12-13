package com.billing.controller;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.billing.dto.ProductPaginationResponse;
import com.billing.dto.ProductPaginationRequest;
import com.billing.models.Product;
import com.billing.service.ProductService;

@RestController
@RequestMapping("/api/products")

public class ProductController 
{
    private final ProductService productService;

    public ProductController(ProductService productService) 
    {
        this.productService = productService;
    }

<<<<<<< Updated upstream
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

    // Pagination endpoint - MUST come before /{id} to avoid route conflict
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
=======
// CREATE - POST
@PostMapping("/createProduct/{tenantId}/{organizationId}")
public Product createProduct(
        @PathVariable("tenantId") String tenantId,
        @PathVariable("organizationId") String organizationId,
>>>>>>> Stashed changes
        @RequestBody Product product) 
{
    
    product.setTenantId(tenantId);
    product.setOrganizationId(organizationId);

    return productService.saveProduct(product);
}

<<<<<<< Updated upstream
=======
// READ - GET by ID
@GetMapping("/getProductById/{tenantId}/{organizationId}/{id}")
public Product getById(
        @PathVariable("tenantId") String tenantId,
        @PathVariable("organizationId") String organizationId,
        @PathVariable("id") Long id) {

    return productService.getProductById(tenantId, organizationId, id);
}


// UPDATE - PUT
@PutMapping("/updateProduct/{id}")
public Product updateProduct(
    @PathVariable Long id,
    @RequestBody Product product) 
{
    return productService.updateProduct(id, product);
}
    

// DELETE
@DeleteMapping("/deleteProduct/{tenantId}/{organizationId}/{id}")
public String deleteProduct(
        @PathVariable("tenantId") String tenantId,
        @PathVariable("organizationId") String organizationId,
        @PathVariable("id") Long id) 
{
    productService.deleteById(tenantId, organizationId, id);
    return "Product deactivated successfully!";
}



// POST mapping for pagination
@PostMapping("/getAllProductList/{tenantId}/{organizationId}")
public ResponseEntity<Map<String, Object>> getAllProductList(
        @PathVariable String tenantId,
        @PathVariable String organizationId,
        @RequestBody ProductPaginationRequest request) {

    List<ProductPaginationResponse> data = productService.getAllProductList(
            tenantId,
            organizationId,
            request.getSearchText(),
            request.getOffsetStart(),
            request.getRowsPerPage()
    );

    Long totalRows = data.isEmpty() ? 0 : data.get(0).getTotalRowsCount();

    Map<String, Object> response = new HashMap<>();
    response.put("totalRows", totalRows);
    response.put("rows", data);

    return ResponseEntity.ok(response);
}

>>>>>>> Stashed changes
}
