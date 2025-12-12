# Complete File-by-File Flow Explanation for CRUD Operations

## File Structure Overview
```
src/main/java/com/billing/
├── BillingApplication.java          (Main entry point)
├── models/Product.java              (Entity/Database model)
├── controller/ProductController.java (REST API endpoints)
├── service/ProductService.java      (Service interface)
├── service/impl/ProductServiceImpl.java (Business logic)
├── repository/ProductRepository.java (Database operations)
├── exception/
│   ├── ProductNotFoundException.java
│   ├── GlobalExceptionHandler.java
│   └── ErrorResponse.java
├── config/
│   ├── CorsConfig.java
│   └── StoredProcedureLoader.java
└── dto/
    ├── ProductDto.java
    └── ProductPaginationResponse.java
```

---

## 1. BillingApplication.java
**Purpose:** Main entry point - Starts Spring Boot application

### CREATE Operation Flow:
1. Application starts → `main()` method executes
2. `SpringApplication.run()` initializes Spring context
3. Spring scans all `@Component`, `@Service`, `@Repository`, `@Controller` annotations
4. All beans are created and dependency injection happens
5. Server starts on port 8080
6. Application ready to receive requests

### READ Operation Flow:
- Same as CREATE - Application must be running
- No specific execution for READ, just serves requests

### UPDATE Operation Flow:
- Same as CREATE - Application must be running

### DELETE Operation Flow:
- Same as CREATE - Application must be running

**Key Point:** This file runs once at startup, initializes everything, then stays running to handle all requests.

---

## 2. models/Product.java
**Purpose:** Entity class - Maps Java object to database table `product_table`

### CREATE Operation Flow:
1. Client sends JSON in request body
2. Spring automatically converts JSON to `Product` object using Jackson
3. Fields are mapped: `product_name` → `product_name` column, `tenant_id` → `tenant_id` column
4. `@Id` and `@GeneratedValue` annotations tell Hibernate to auto-generate ID
5. `@Column` annotations map Java field names to database column names
6. Product object is created with data from JSON
7. Object is passed to Service layer
8. After save, database sets ID, which is returned in the Product object

### READ Operation Flow:
1. Repository queries database and gets row data
2. Hibernate automatically converts database row to `Product` object
3. Each column value is mapped to corresponding field using `@Column` annotations
4. `Product` object is returned to Service
5. Service returns to Controller
6. Spring converts `Product` object to JSON using Jackson
7. JSON sent to client

### UPDATE Operation Flow:
1. Client sends JSON with updated fields
2. Spring converts JSON to `Product` object (partial data)
3. Service gets existing `Product` from database (full object)
4. Service merges: updates fields from new object, preserves others
5. Updated `Product` object saved to database
6. Hibernate updates corresponding database row
7. Updated object returned as JSON

### DELETE Operation Flow:
1. Service gets `Product` object by ID (to verify existence)
2. Repository deletes by ID
3. Hibernate removes row from `product_table`
4. No Product object returned (void operation)

**Key Point:** This file defines the structure - how data is stored in memory (Java object) and database (table row).

---

## 3. controller/ProductController.java
**Purpose:** REST API endpoints - Receives HTTP requests, returns HTTP responses

### CREATE Operation Flow:
1. **@PostMapping** annotation intercepts POST request to `/api/products`
2. **@RequestParam** extracts `tenant_id` and `organization_id` from URL query string
3. **@RequestBody** converts JSON body to `Product` object automatically
4. Controller calls `productService.saveProduct(tenant_id, organization_id, product)`
5. Service returns saved `Product` object
6. Controller returns `Product` object
7. Spring automatically converts `Product` to JSON
8. JSON sent to client with HTTP 200 status

### READ Operation Flow (GET All):
1. **@GetMapping** intercepts GET request to `/api/products`
2. **@RequestParam(required = false)** extracts optional `tenant_id` and `organization_id`
3. Controller calls `productService.getProductsByTenantAndOrg(tenant_id, organization_id)`
4. Service returns `List<Product>`
5. Controller returns list
6. Spring converts list to JSON array
7. JSON array sent to client

### READ Operation Flow (GET by ID):
1. **@GetMapping("/{id}")** intercepts GET request to `/api/products/1`
2. **@PathVariable** extracts `id` from URL path (1)
3. Controller calls `productService.getProductById(1)`
4. Service returns `Product` object
5. Controller returns `Product`
6. Spring converts to JSON
7. JSON sent to client

### UPDATE Operation Flow:
1. **@PutMapping("/{id}")** intercepts PUT request to `/api/products/1`
2. **@PathVariable** extracts `id` from URL (1)
3. **@RequestBody** converts JSON body to `Product` object
4. Controller calls `productService.updateProduct(1, product)`
5. Service returns updated `Product` object
6. Controller returns `Product`
7. Spring converts to JSON
8. JSON sent to client

### DELETE Operation Flow:
1. **@DeleteMapping("/{id}")** intercepts DELETE request to `/api/products/1`
2. **@PathVariable** extracts `id` from URL (1)
3. Controller calls `productService.deleteProduct(1)`
4. Service returns void (nothing)
5. Controller returns String: "Product deleted successfully!"
6. String sent to client as JSON

**Key Point:** Controller is the entry point - receives HTTP requests, extracts parameters, calls service, returns responses.

---

## 4. service/ProductService.java
**Purpose:** Service interface - Defines contract for business logic

### All Operations Flow:
1. Interface defines method signatures (no implementation)
2. Used for dependency injection - Controller depends on interface, not implementation
3. `ProductServiceImpl` implements this interface
4. Spring injects implementation when Controller needs it
5. Provides abstraction - can swap implementations without changing Controller

**Key Point:** Interface defines what operations are available, implementation does the work.

---

## 5. service/impl/ProductServiceImpl.java
**Purpose:** Business logic implementation - Validates, processes, coordinates operations

### CREATE Operation Flow:
1. **@Service** annotation makes this a Spring bean
2. Constructor receives `ProductRepository` (dependency injection)
3. `saveProduct()` method called with tenant_id, organization_id, product
4. **@Transactional** starts database transaction
5. **Validation Step 1:** Checks tenant_id is positive → throws exception if invalid
6. **Validation Step 2:** Checks organization_id is positive → throws exception if invalid
7. **Validation Step 3:** Checks product is not null → throws exception if null
8. **Validation Step 4:** Checks SKU is not empty → throws exception if empty
9. **Validation Step 5:** Checks product_name is not empty → throws exception if empty
10. **Validation Step 6:** Checks prices and stock are not negative → throws exception if negative
11. **Business Logic:** Sets tenant_id and organization_id to product object
12. **Business Logic:** Sets created timestamp to current time (if not set)
13. **Business Logic:** Sets modified timestamp to current time
14. **Business Logic:** Sets default is_active = 1 (if not provided)
15. Calls `productRepository.save(product)` → passes to Repository layer
16. Repository returns saved product with ID
17. Transaction commits
18. Returns saved product to Controller

### READ Operation Flow (GET All):
1. `getProductsByTenantAndOrg()` method called
2. **Logic Step 1:** If both tenant_id and organization_id are null → calls `repository.findAll()`
3. **Logic Step 2:** If both provided → calls `repository.findByTenantIdAndOrganizationId()`
4. **Logic Step 3:** If only tenant_id → calls `repository.findByTenantIdAndIsActive(tenant_id, 1)`
5. **Logic Step 4:** If only organization_id → calls `repository.findByOrganizationIdAndIsActive()`
6. Repository returns `List<Product>`
7. Service returns list to Controller

### READ Operation Flow (GET by ID):
1. `getProductById()` method called with ID
2. **Validation:** Checks ID is positive → throws exception if invalid
3. Calls `productRepository.findById(id)`
4. Repository returns `Optional<Product>`
5. **If not found:** `orElseThrow()` throws `ProductNotFoundException`
6. **If found:** Returns Product object to Controller

### UPDATE Operation Flow:
1. `updateProduct()` method called with ID and product
2. **@Transactional** starts database transaction
3. **Validation Step 1:** Checks ID is positive → throws exception if invalid
4. **Validation Step 2:** Checks product is not null → throws exception if null
5. **Validation Step 3:** Checks prices and stock are not negative → throws exception if negative
6. Calls `productRepository.findById(id)` to get existing product
7. **If not found:** Throws `ProductNotFoundException` (404 error)
8. **If found:** Gets existing product from database
9. **Preserve Logic:** Sets ID from existing product (cannot change)
10. **Preserve Logic:** Sets tenant_id from existing (cannot change)
11. **Preserve Logic:** Sets organization_id from existing (cannot change)
12. **Preserve Logic:** Sets created timestamp from existing (never changes)
13. **Update Logic:** Sets modified timestamp to current time
14. **Preserve Logic:** If is_active not provided, uses existing value
15. **Preserve Logic:** If SKU not provided, uses existing value
16. **Preserve Logic:** If product_name not provided, uses existing value
17. **Preserve Logic:** If category not provided, uses existing value
18. **Preserve Logic:** If prices/stock not provided, uses existing values
19. **Preserve Logic:** If status not provided, uses existing value
20. Calls `productRepository.save(product)` with merged product
21. Repository updates database
22. Transaction commits
23. Returns updated product to Controller

### DELETE Operation Flow:
1. `deleteProduct()` method called with ID
2. **@Transactional** starts database transaction
3. **Validation:** Checks ID is positive → throws exception if invalid
4. Calls `productRepository.findById(id)` to verify product exists
5. **If not found:** Throws `ProductNotFoundException` (404 error)
6. **If found:** Calls `productRepository.deleteById(id)`
7. Repository deletes from database
8. Transaction commits
9. Returns void (nothing) to Controller

**Key Point:** Service layer contains all business logic, validation, and coordinates between Controller and Repository.

---

## 6. repository/ProductRepository.java
**Purpose:** Database operations - Executes SQL queries, interacts with database

### CREATE Operation Flow:
1. **@Repository** annotation makes this a Spring Data JPA repository
2. Extends `JpaRepository<Product, Long>` - provides basic CRUD methods
3. Service calls `save(product)` method (inherited from JpaRepository)
4. Hibernate (JPA implementation) converts Product object to SQL INSERT
5. SQL executed: `INSERT INTO product_table (sku, tenant_id, ...) VALUES (?, ?, ...)`
6. Database auto-generates ID (because of `@GeneratedValue`)
7. Database returns saved row with ID
8. Hibernate converts row back to Product object
9. Product object with ID returned to Service

### READ Operation Flow (GET All - No Filters):
1. Service calls `findAll()` method (inherited from JpaRepository)
2. Hibernate generates SQL: `SELECT * FROM product_table`
3. Database executes query, returns all rows
4. Hibernate converts each row to Product object
5. Returns `List<Product>` to Service

### READ Operation Flow (GET All - With Filters):
1. Service calls `findByTenantIdAndOrganizationId(tenantId, organizationId)`
2. **@Query** annotation provides JPQL query: `SELECT p FROM Product p WHERE p.tenant_id = :tenantId AND p.organization_id = :organizationId`
3. Hibernate converts JPQL to SQL: `SELECT * FROM product_table WHERE tenant_id = ? AND organization_id = ?`
4. **@Param** annotations bind method parameters to query parameters
5. Database executes query with parameters
6. Database returns matching rows
7. Hibernate converts rows to Product objects
8. Returns `List<Product>` to Service

### READ Operation Flow (GET by ID):
1. Service calls `findById(id)` method (inherited from JpaRepository)
2. Hibernate generates SQL: `SELECT * FROM product_table WHERE id = ?`
3. Database executes query with ID parameter
4. **If found:** Database returns row, Hibernate converts to Product, returns `Optional<Product>` with value
5. **If not found:** Returns `Optional<Product>.empty()`
6. Service handles Optional (throws exception if empty)

### UPDATE Operation Flow:
1. Service calls `save(product)` method (same as CREATE)
2. Hibernate checks if Product has ID set
3. **Since ID exists:** Hibernate generates UPDATE SQL instead of INSERT
4. SQL executed: `UPDATE product_table SET sku = ?, product_name = ?, ... WHERE id = ?`
5. Database updates row with new values
6. Database returns updated row
7. Hibernate converts to Product object
8. Returns updated Product to Service

### DELETE Operation Flow:
1. Service calls `deleteById(id)` method (inherited from JpaRepository)
2. Hibernate generates SQL: `DELETE FROM product_table WHERE id = ?`
3. Database executes DELETE query with ID parameter
4. Database removes row
5. Returns void (nothing) to Service

**Key Point:** Repository handles all database communication - converts Java objects to SQL and SQL results to Java objects.

---

## 7. exception/ProductNotFoundException.java
**Purpose:** Custom exception for when product is not found

### CREATE Operation Flow:
- Not used in CREATE (product doesn't exist yet)

### READ Operation Flow:
1. When `findById()` returns empty Optional
2. Service calls `orElseThrow(() -> new ProductNotFoundException(id))`
3. Exception object created with message: "Product with id X not found"
4. Exception thrown up the call stack
5. Caught by GlobalExceptionHandler

### UPDATE Operation Flow:
1. When `findById()` returns empty Optional (product doesn't exist)
2. Service throws `ProductNotFoundException`
3. Exception caught by GlobalExceptionHandler
4. Returns 404 error to client

### DELETE Operation Flow:
1. When `findById()` returns empty Optional (product doesn't exist)
2. Service throws `ProductNotFoundException`
3. Exception caught by GlobalExceptionHandler
4. Returns 404 error to client

**Key Point:** Custom exception provides clear error messages and specific error type.

---

## 8. exception/GlobalExceptionHandler.java
**Purpose:** Catches all exceptions and returns proper HTTP error responses

### CREATE Operation Flow:
1. If validation fails in Service → throws `IllegalArgumentException`
2. Exception bubbles up through Controller
3. **@RestControllerAdvice** annotation makes this handler catch all exceptions
4. **@ExceptionHandler(IllegalArgumentException.class)** catches the exception
5. Handler creates `ErrorResponse` object with:
   - status: 400 (Bad Request)
   - error: "Invalid Input"
   - message: exception message
6. Returns `ResponseEntity<ErrorResponse>` with HTTP 400 status
7. Spring converts ErrorResponse to JSON
8. JSON error response sent to client

### READ Operation Flow:
1. If product not found → `ProductNotFoundException` thrown
2. **@ExceptionHandler(ProductNotFoundException.class)** catches it
3. Creates ErrorResponse with:
   - status: 404 (Not Found)
   - error: "Product Not Found"
   - message: "Product with id X not found"
4. Returns HTTP 404 response to client

### UPDATE Operation Flow:
1. If validation fails → `IllegalArgumentException` → 400 error
2. If product not found → `ProductNotFoundException` → 404 error
3. If unexpected error → `RuntimeException` → 500 error

### DELETE Operation Flow:
1. If product not found → `ProductNotFoundException` → 404 error
2. If validation fails → `IllegalArgumentException` → 400 error

**Key Point:** Centralized error handling - all exceptions caught here, converted to proper HTTP error responses.

---

## 9. exception/ErrorResponse.java
**Purpose:** Standard error response format

### All Operations Flow:
1. When exception occurs, GlobalExceptionHandler creates ErrorResponse object
2. Sets status code (400, 404, 500, etc.)
3. Sets error type ("Invalid Input", "Product Not Found", etc.)
4. Sets error message (detailed message from exception)
5. Spring converts ErrorResponse object to JSON:
   ```json
   {
     "status": 404,
     "error": "Product Not Found",
     "message": "Product with id 999 not found"
   }
   ```
6. JSON sent to client with appropriate HTTP status code

**Key Point:** Standardized error format - all errors return same structure.

---

## 10. config/CorsConfig.java
**Purpose:** Configures CORS (Cross-Origin Resource Sharing) - allows frontend to call API

### All Operations Flow:
1. **@Configuration** annotation loads this at startup
2. **addCorsMappings()** method configures CORS for all endpoints (`/**`)
3. Allows all origins (`*`), all methods (GET, POST, PUT, DELETE), all headers
4. **@Bean corsFilter()** creates CorsFilter that processes every request
5. **For every HTTP request (CREATE, READ, UPDATE, DELETE):**
   - CorsFilter intercepts request
   - Checks if request has Origin header (from browser)
   - Adds CORS headers to response:
     - `Access-Control-Allow-Origin: *`
     - `Access-Control-Allow-Methods: GET, POST, PUT, DELETE`
     - `Access-Control-Expose-Headers: *`
   - Allows request to proceed
6. Response sent to client with CORS headers
7. Browser allows frontend to read response (no CORS error)

**Key Point:** Runs for every request - adds CORS headers so frontend can call API from different origin.

---

## 11. config/StoredProcedureLoader.java
**Purpose:** Creates stored procedure in database at startup

### CREATE Operation Flow:
1. **@Component** annotation makes this a Spring bean
2. **@PostConstruct** annotation runs `loadProcedure()` after all beans are created
3. Gets database connection via `JdbcTemplate`
4. Executes SQL: `DROP PROCEDURE IF EXISTS product_getall`
5. Executes SQL: `CREATE PROCEDURE product_getall() ...`
6. Stored procedure created in database
7. Prints success message
8. **Not directly used in CREATE operation** - just sets up database

### READ Operation Flow:
- Stored procedure `product_getall` is available but not used in regular READ
- Used only for pagination endpoint

### UPDATE Operation Flow:
- Not used in UPDATE

### DELETE Operation Flow:
- Not used in DELETE

**Key Point:** Runs once at startup - prepares database stored procedures.

---

## 12. dto/ProductDto.java
**Purpose:** Data Transfer Object - Currently not actively used (Product entity used instead)

### All Operations Flow:
- This file exists but is not currently used in the codebase
- Could be used to separate API response format from database entity
- Currently, Product entity is used directly for requests and responses

**Key Point:** Available for future use if needed to separate API contract from database structure.

---

## 13. dto/ProductPaginationResponse.java
**Purpose:** DTO for pagination endpoint response

### CREATE Operation Flow:
- Not used in CREATE

### READ Operation Flow (Pagination):
1. Repository calls stored procedure, returns `List<Object[]>` (raw database results)
2. Service maps each `Object[]` array to `ProductPaginationResponse` object
3. Extracts each field from array:
   - `row[0]` → `id`
   - `row[1]` → `sku`
   - `row[2]` → `category`
   - `row[3]` → `salePrice`
   - `row[4]` → `stock`
   - `row[5]` → `status`
   - `row[6]` → `created`
   - `row[7]` → `createdBy`
   - `row[8]` → `modified`
   - `row[9]` → `modifiedBy`
   - `row[10]` → `totalRowsCount`
4. Creates `ProductPaginationResponse` object with extracted values
5. Returns list of `ProductPaginationResponse` objects
6. Controller returns list
7. Spring converts to JSON array
8. Client receives paginated results with total count

### UPDATE Operation Flow:
- Not used in UPDATE

### DELETE Operation Flow:
- Not used in DELETE

**Key Point:** Used only for pagination - provides different response structure than regular Product entity.

---

## Complete Flow Summary for Each Operation

### CREATE (POST /api/products):
```
Client Request (JSON)
  ↓
CorsConfig (adds CORS headers)
  ↓
ProductController.createProduct()
  ↓
ProductServiceImpl.saveProduct() [Validates, sets timestamps]
  ↓
ProductRepository.save() [Hibernate converts to SQL]
  ↓
Database INSERT
  ↓
Product object with ID returned
  ↓
Controller returns Product
  ↓
Spring converts to JSON
  ↓
Client receives JSON response
```

### READ (GET /api/products/{id}):
```
Client Request
  ↓
CorsConfig (adds CORS headers)
  ↓
ProductController.getById()
  ↓
ProductServiceImpl.getProductById() [Validates ID]
  ↓
ProductRepository.findById() [Hibernate converts to SQL]
  ↓
Database SELECT WHERE id = ?
  ↓
Product object returned (or Optional.empty())
  ↓
If not found → ProductNotFoundException → GlobalExceptionHandler → 404 error
If found → Product returned → Controller → JSON → Client
```

### UPDATE (PUT /api/products/{id}):
```
Client Request (JSON with updates)
  ↓
CorsConfig (adds CORS headers)
  ↓
ProductController.updateProduct()
  ↓
ProductServiceImpl.updateProduct() [Validates, preserves fields, merges data]
  ↓
ProductRepository.findById() [Get existing]
  ↓
ProductRepository.save() [Hibernate converts to SQL UPDATE]
  ↓
Database UPDATE
  ↓
Updated Product returned
  ↓
Controller → JSON → Client
```

### DELETE (DELETE /api/products/{id}):
```
Client Request
  ↓
CorsConfig (adds CORS headers)
  ↓
ProductController.delete()
  ↓
ProductServiceImpl.deleteProduct() [Validates, checks existence]
  ↓
ProductRepository.findById() [Verify exists]
  ↓
ProductRepository.deleteById() [Hibernate converts to SQL DELETE]
  ↓
Database DELETE
  ↓
Controller returns success message
  ↓
Client receives "Product deleted successfully!"
```

---

## Key Execution Points:

1. **BillingApplication** - Starts everything, runs continuously
2. **CorsConfig** - Processes every request, adds CORS headers
3. **ProductController** - Entry point, receives HTTP requests
4. **ProductServiceImpl** - Business logic, validation, coordination
5. **ProductRepository** - Database operations, SQL execution
6. **Product** (Entity) - Data structure, object-relational mapping
7. **GlobalExceptionHandler** - Catches errors, returns proper responses
8. **Exceptions** - Provide specific error types and messages

All files work together in a layered architecture: Controller → Service → Repository → Database


