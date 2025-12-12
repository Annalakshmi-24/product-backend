# API Action Flow Explanation - Simple Guide

## 1. CREATE PRODUCT (POST /api/products)

**Flow:**
1. Client sends POST request to `/api/products?tenant_id=1&organization_id=1`
2. Controller receives request with tenant_id and organization_id as URL parameters
3. Controller receives product data in request body (JSON)
4. Controller calls `productService.saveProduct()`
5. Service validates: checks if tenant_id, organization_id are positive numbers
6. Service validates: checks if SKU and product_name are not empty
7. Service validates: checks if prices and stock are not negative
8. Service sets tenant_id and organization_id from URL parameters to product object
9. Service sets created timestamp to current time
10. Service sets modified timestamp to current time
11. Service sets default is_active = 1 if not provided
12. Service calls repository.save() to save product to database
13. Database creates new product record with auto-generated ID
14. Service returns saved product with all fields including new ID
15. Controller returns product as JSON response to client

---

## 2. GET ALL PRODUCTS (GET /api/products)

**Flow:**
1. Client sends GET request to `/api/products` (with optional filters)
2. Controller receives request with optional tenant_id and organization_id as query parameters
3. Controller calls `productService.getProductsByTenantAndOrg()`
4. Service checks: if both tenant_id and organization_id are null → returns all products
5. Service checks: if both are provided → filters by both
6. Service checks: if only tenant_id provided → filters by tenant_id and is_active=1
7. Service checks: if only organization_id provided → filters by organization_id and is_active=1
8. Service calls repository method based on filter condition
9. Repository executes database query (JPQL or findAll)
10. Database returns list of products matching criteria
11. Repository returns list to service
12. Service returns list to controller
13. Controller converts list to JSON array
14. Client receives JSON array of all products

---

## 3. GET PRODUCT BY ID (GET /api/products/{id})

**Flow:**
1. Client sends GET request to `/api/products/1` (1 is the product ID)
2. Controller extracts ID from URL path using @PathVariable
3. Controller calls `productService.getProductById(1)`
4. Service validates: checks if ID is positive number
5. Service calls `repository.findById(1)`
6. Repository queries database: SELECT * FROM product_table WHERE id = 1
7. Database returns product record if found, or null if not found
8. If product not found: Service throws ProductNotFoundException
9. Exception handler catches exception and returns 404 error with message
10. If product found: Repository returns Product object to service
11. Service returns Product to controller
12. Controller converts Product to JSON
13. Client receives JSON object with product details
14. If not found, client receives 404 error with error message

---

## 4. UPDATE PRODUCT (PUT /api/products/{id})

**Flow:**
1. Client sends PUT request to `/api/products/1` with updated data in body
2. Controller extracts ID from URL path
3. Controller receives updated product data in request body (JSON)
4. Controller calls `productService.updateProduct(1, product)`
5. Service validates: checks if ID is positive number
6. Service validates: checks if product object is not null
7. Service validates: checks if prices and stock are not negative
8. Service calls `repository.findById(1)` to get existing product
9. If product not found: throws ProductNotFoundException (404 error)
10. If found: Service preserves tenant_id and organization_id from existing product
11. Service preserves created timestamp from existing product
12. Service updates modified timestamp to current time
13. Service preserves fields not provided in update (sku, category, etc. if null)
14. Service updates only fields provided in request body
15. Service calls `repository.save()` with updated product
16. Database updates product record with new values
17. Repository returns updated product to service
18. Service returns updated product to controller
19. Controller converts to JSON and returns to client
20. Client receives updated product with all fields

---

## 5. DELETE PRODUCT (DELETE /api/products/{id})

**Flow:**
1. Client sends DELETE request to `/api/products/1`
2. Controller extracts ID from URL path
3. Controller calls `productService.deleteProduct(1)`
4. Service validates: checks if ID is positive number
5. Service calls `repository.findById(1)` to check if product exists
6. If product not found: throws ProductNotFoundException (404 error)
7. If found: Service calls `repository.deleteById(1)`
8. Repository executes DELETE query in database
9. Database removes product record from product_table
10. Repository confirms deletion
11. Service returns void (nothing)
12. Controller returns success message: "Product deleted successfully!"
13. Client receives success message as string
14. If not found, client receives 404 error

---

## 6. GET PRODUCTS WITH PAGINATION (GET /api/products/pagination)

**Flow:**
1. Client sends GET request to `/api/products/pagination?tenantId=1&organizationId=1&offsetStart=1&rowsPerPage=10`
2. Controller receives tenantId, organizationId, searchText (optional), offsetStart, rowsPerPage as query parameters
3. Controller calls `productService.getProductsPagination()`
4. Service validates: checks if tenantId and organizationId are positive numbers
5. Service validates: checks if offsetStart is at least 1
6. Service validates: checks if rowsPerPage is at least 1
7. Service calls `repository.getProductsPagination()` with all parameters
8. Repository executes stored procedure: `CALL getProductsPagination1(...)`
9. Stored procedure calculates: startRow = (offsetStart - 1) * rowsPerPage
10. Stored procedure queries database with filters: tenant_id, organization_id, status=1, searchText
11. Database returns paginated results with total count
12. Repository receives array of Object[] results from stored procedure
13. Service maps each Object[] array to ProductPaginationResponse DTO
14. Service extracts: id, sku, category, salePrice, stock, status, created, createdBy, modified, modifiedBy, totalRowsCount
15. Service validates: checks if result array has at least 11 elements
16. Service converts each field from Object to proper type (Long, String, Double, etc.)
17. Service returns list of ProductPaginationResponse objects
18. Controller converts list to JSON array
19. Client receives JSON array with paginated products and total count
20. Each product in response includes totalRowsCount for pagination UI

---

## Summary of All Endpoints:

1. **POST /api/products** - Creates new product (requires tenant_id & organization_id in URL)
2. **GET /api/products** - Gets all products (optional filters: tenant_id, organization_id)
3. **GET /api/products/{id}** - Gets single product by ID
4. **PUT /api/products/{id}** - Updates existing product by ID
5. **DELETE /api/products/{id}** - Deletes product by ID
6. **GET /api/products/pagination** - Gets paginated products with search

---

## Common Flow Pattern:

**All requests follow this pattern:**
1. Client sends HTTP request → Controller receives it
2. Controller validates URL/path parameters → Calls Service
3. Service validates business logic → Calls Repository
4. Repository executes database query → Returns data
5. Service processes data → Returns to Controller
6. Controller converts to JSON → Returns to Client
7. If error occurs → Exception Handler catches it → Returns error response

---

## Error Handling Flow:

1. Any validation fails → Service throws IllegalArgumentException
2. Product not found → Service throws ProductNotFoundException
3. Exception Handler catches exception → Creates ErrorResponse object
4. ErrorResponse contains: status code, error type, error message
5. Controller returns ErrorResponse as JSON with appropriate HTTP status
6. Client receives error response with clear message

---

## Database Operations:

1. **CREATE**: INSERT INTO product_table → Auto-generates ID
2. **READ**: SELECT FROM product_table → Returns records
3. **UPDATE**: UPDATE product_table SET ... WHERE id = ? → Modifies record
4. **DELETE**: DELETE FROM product_table WHERE id = ? → Removes record
5. **PAGINATION**: Stored procedure with LIMIT and OFFSET → Returns paginated results

---

## Key Points to Remember:

1. **POST** = Create new (no ID in URL, ID in query params)
2. **GET** = Read/Retrieve data (no body, only URL/query params)
3. **PUT** = Update existing (ID in URL, data in body)
4. **DELETE** = Remove (ID in URL, no body)
5. **tenant_id & organization_id** = Always preserved, cannot be changed via update
6. **created timestamp** = Set once on create, never changed
7. **modified timestamp** = Updated every time product is modified
8. **is_active** = Defaults to 1 if not provided
9. **Validation** = Happens in Service layer before database operations
10. **Error handling** = Global exception handler catches all exceptions
11. **CORS** = Enabled for all origins, allows cross-origin requests
12. **Transactions** = All write operations (POST, PUT, DELETE) are transactional
13. **Auto-timestamps** = Created and modified timestamps set automatically
14. **Data preservation** = Update preserves existing data if not provided in request


