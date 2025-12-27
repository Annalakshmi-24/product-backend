package com.billing.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

// Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import com.billing.dto.ProductPaginationResponse;
import com.billing.dto.ProductIdNameDTO;
import com.billing.dto.ProductPaginationRequest;
import com.billing.models.Product;
import com.billing.interfaces.ProductService;
import com.billing.service.ProductServiceImpl;
import com.billing.exception.ProductCustomExceptions;

@RestController
@RequestMapping("/api/products")
public class ProductController 
{
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) 
    {
        this.productService = productService;
    }

    private ResponseEntity<Map<String, Object>> errorResponse(
            HttpStatus status,
            String message) 
    {
        Map<String, Object> error = new HashMap<>();
        error.put("status", status.value());
        error.put("error", message);
        error.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(status).body(error);
    }

    //  CREATE  
    @PostMapping("/createProduct/{tenantId}/{organizationId}")
    public ResponseEntity<?> createProduct(
            @PathVariable String tenantId,
            @PathVariable String organizationId,
            @RequestBody Product product) 
    {
        try 
        {
            log.info("Create Product API called | tenantId={} | organizationId={}", tenantId, organizationId);

            product.setTenantId(tenantId);
            product.setOrganizationId(organizationId);

            Product savedProduct = productService.saveProduct(product);

            log.info("Product created successfully | productId={}", savedProduct.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);

        } catch (DataIntegrityViolationException ex) 
        {
            log.error("DB constraint violation", ex);
            return errorResponse(HttpStatus.BAD_REQUEST,
                    "Invalid database values. Please verify foreign key or unique fields.");

        } catch (Exception ex) {
            log.error("Error creating product", ex);
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to create product");
        }
    }


    //  UPDATE  
    @PutMapping("/updateProduct/{tenantId}/{organizationId}/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String tenantId,
            @PathVariable String organizationId,
            @PathVariable Long id,
            @RequestBody Product product) 
    {
        try {
            log.info("Update Product API called | productId={}", id);

            Product updated =
                    productService.updateProduct(id, tenantId, organizationId, product);

            return ResponseEntity.ok(updated);

        } catch (DataIntegrityViolationException ex) {
            log.error("DB constraint error while updating product", ex);
            return errorResponse(HttpStatus.BAD_REQUEST,
                    "Invalid request data. ");

        } catch (ProductCustomExceptions.ResourceNotFoundException ex) {
            log.warn(ex.getMessage());
            return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());

        } catch (Exception ex) {
            log.error("Error updating product", ex);
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to update product");
        }
    }

    
    //  GET BY ID  
    @GetMapping("/getProductById/{tenantId}/{organizationId}/{id}")
    public ResponseEntity<?> getById(
            @PathVariable String tenantId,
            @PathVariable String organizationId,
            @PathVariable Long id) 
    {
        try {
            log.info("Get Product By ID API called | productId={}", id);

            Product product = productService.getProductById(tenantId, organizationId, id);

            if (product == null) 
                {
                throw new ProductCustomExceptions.ResourceNotFoundException(
                        "Product not found with id: " + id);
                }

            return ResponseEntity.ok(product);

        } catch (ProductCustomExceptions.ResourceNotFoundException ex) {
            log.warn(ex.getMessage());
            return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());

        } catch (Exception ex) {
            log.error("Error fetching product", ex);
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to fetch product");
        }
    }

    
    //GetAllList(ProductName and id)
   @PostMapping("/getAllProduct/{tenantId}/{organizationId}")
    public ResponseEntity<?> getAllProducts(
        @PathVariable String tenantId,
        @PathVariable String organizationId) 
        {

    try {
        log.info("Get All Products API called | tenantId={}, organizationId={}",
                tenantId, organizationId);

        List<ProductIdNameDTO> products = productService.getAllProducts(tenantId, organizationId);

        return ResponseEntity.ok(products);

    } catch (ProductCustomExceptions.ResourceNotFoundException ex) 
    {
        log.warn("Products not found | {}", ex.getMessage());
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());

    } catch (Exception ex) 
    {
        log.error("Error while fetching products", ex);
        return errorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to fetch products"
        );
    }
}


    //  GetProductList(Pagination)  
    @PostMapping("/getProductList/{tenantId}/{organizationId}")
    public ResponseEntity<?> getAllProductList(
            @PathVariable String tenantId,
            @PathVariable String organizationId,
            @RequestBody ProductPaginationRequest request) 
    {
        try {
            log.info("Get All Product List API called");

            List<ProductPaginationResponse> data =
                    productService.getAllProductList(
                            tenantId,
                            organizationId,
                            request.getOffsetStart(),
                            request.getRowsPerPage(),
                            request.getSearchText(),
                            request.getSortOrder(),
                             request.getSortColumn()
                    );

            Long totalRows = data.isEmpty() ? 0 : data.get(0).getTotalRowsCount();

            Map<String, Object> response = new HashMap<>();
            response.put("totalRows", totalRows);
            response.put("rows", data);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            log.error("Error fetching product list", ex);
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to fetch product list");
        }
    }


    //  DELETE  
    @DeleteMapping("/deleteProduct/{tenantId}/{organizationId}/{id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable String tenantId,
            @PathVariable String organizationId,
            @PathVariable Long id) 
    {
        try {
            log.warn("Delete Product API called | productId={}", id);

            productService.deleteById(tenantId, organizationId, id);

            return ResponseEntity.ok("Product deactivated successfully!");

        } catch (ProductCustomExceptions.ResourceNotFoundException ex) {
            log.warn(ex.getMessage());
            return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());

        } catch (Exception ex) {
            log.error("Error deleting product", ex);
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete product");
        }
    }



//  METHOD NOT ALLOWED  
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handle405(HttpRequestMethodNotSupportedException ex) {
        return errorResponse(HttpStatus.METHOD_NOT_ALLOWED,
                "HTTP method not allowed for this endpoint");
    }
}


   
    

