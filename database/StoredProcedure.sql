--Pagination:
CREATE  PROCEDURE `getProductsList`(
    IN tenantId VARCHAR(100),
    IN organizationId VARCHAR(100),
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
      COUNT(*) OVER() AS totalRowsCount FROM product_table p WHERE p.is_active = 1
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
    

END


--Calling query
CALL getProductsPagination1(
    1,           
    1,           
    'Product',   -- searchText
    1,           -- offsetStart (page number)
    10           -- rowsPerPage
);





