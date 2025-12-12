-- Stored Procedure: getProductsPagination1
-- Purpose: Get paginated products with search functionality
-- Created: 2025-12-12
-- Database: products

DROP PROCEDURE IF EXISTS getProductsPagination1;

DELIMITER $$

CREATE PROCEDURE getProductsPagination1(
    IN tenantId BIGINT,
    IN organizationId BIGINT,
    IN searchText VARCHAR(255),
    IN offsetStart INT,
    IN rowsPerPage INT
)
BEGIN
    DECLARE startRow INT DEFAULT 0;
    SET startRow = (offsetStart - 1) * rowsPerPage;

    SELECT
        p.id AS id,
        p.sku AS sku,
        p.category AS category,
        p.sales_price AS salePrice,
        p.stock AS stock,
        p.status AS status,
        p.created AS created,
        p.created_by AS createdBy,
        p.modified AS modified,
        p.modified_by AS modifiedBy,
        COUNT(*) OVER() AS totalRowsCount
    FROM product_table p
    WHERE (p.status = '1' OR p.status = 1)
      AND p.tenant_id = tenantId
      AND p.organization_id = organizationId
      AND (
            searchText IS NULL
            OR searchText = ''
            OR p.product_name LIKE CONCAT('%', searchText, '%')
            OR p.sku LIKE CONCAT('%', searchText, '%')
          )
    ORDER BY p.created DESC
    LIMIT startRow, rowsPerPage;

END $$

DELIMITER ;

-- Usage Example:
-- CALL getProductsPagination1(1, 1, 'Product', 1, 10);
-- Parameters:
--   tenantId: Tenant ID (required)
--   organizationId: Organization ID (required)
--   searchText: Search term (optional, can be NULL or empty)
--   offsetStart: Page number (starts from 1)
--   rowsPerPage: Number of items per page

