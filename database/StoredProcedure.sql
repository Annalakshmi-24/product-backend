DELIMITER //
CREATE PROCEDURE GetAllProducts()
BEGIN
SELECT
        id,
        sku,
        tenant_id,
        organizational_id,
        category,
       status,
        created_by,
        modified_by,
        is_active,
        category,
        stock,
        purchase_price,
        sales_price
FROM products;
END //
DELIMITER ;

--Pagination:
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
        p.sale_price AS salePrice,
        p.stock AS stock,
        p.status AS status,
        p.created AS created,
        p.created_by AS createdBy,
        p.modified AS modified,
        p.modified_by AS modifiedBy,
        COUNT(*) OVER() AS totalRowsCount
    FROM product_table p
    WHERE p.status = 1
      AND p.tenant_id = tenantId
      AND p.organizational_id = organizationId
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

CALL getProductsPagination1(
    1,           
    1,           
    'Product',   -- searchText
    1,           -- offsetStart (page number)
    10           -- rowsPerPage
);
