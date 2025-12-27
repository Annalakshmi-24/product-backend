--Stored procedure:

USE `azairebilling`;
DROP procedure IF EXISTS `azairebilling`.`getProductsList`;

DELIMITER $$
USE `azairebilling`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getProductsList`(
    IN tenantId VARCHAR(50),
    IN organizationId VARCHAR(50),
    IN offsetStart INT,
    IN rowsPerPage INT,
    IN searchText VARCHAR(255),
    IN sortOrder VARCHAR(4),
	IN sortColumn VARCHAR(50)
)
BEGIN
    DECLARE startRow INT;

    SET startRow = (offsetStart - 1) * rowsPerPage;
	SELECT
        p.id AS id,
        p.sku AS sku,
        p.category_id AS categoryId,
        p.product_name AS productName,
        p.sales_price AS salePrice,
        p.current_stock AS currentStock,
        p.status AS status,
        p.created AS created,
        p.created_by AS createdBy,
        p.modified AS modified,
        p.modified_by AS modifiedBy,
        p.code AS code,
		p.unit AS unit,
        p.tax_rate AS taxRate,
        COUNT(*) OVER () AS totalRowsCount
    FROM product_table p
    WHERE p.active = 1
      AND p.tenant_id = tenantId
      AND p.organization_id = organizationId
      AND (
            searchText IS NULL
            OR searchText = ''
            OR p.product_name LIKE CONCAT('%', searchText, '%')
            OR p.sku LIKE CONCAT('%', searchText, '%')
          )

    /*Dynamic sorting */
    ORDER BY
        CASE 
            WHEN sortColumn = 'productName' AND sortOrder = 'ASC' THEN p.product_name
        END ASC,
        CASE 
            WHEN sortColumn = 'productName' AND sortOrder = 'DESC' THEN p.product_name
        END DESC,

        CASE 
            WHEN sortColumn = 'sku' AND sortOrder = 'ASC' THEN p.sku
        END ASC,
        CASE 
            WHEN sortColumn = 'sku' AND sortOrder = 'DESC' THEN p.sku
        END DESC,

        CASE 
            WHEN sortColumn = 'salesPrice' AND sortOrder = 'ASC' THEN p.sales_price
        END ASC,
        CASE 
            WHEN sortColumn = 'salesPrice' AND sortOrder = 'DESC' THEN p.sales_price
        END DESC,

        CASE 
            WHEN sortColumn = 'currentStock' AND sortOrder = 'ASC' THEN p.current_stock
        END ASC,
        CASE 
            WHEN sortColumn = 'currentStock' AND sortOrder = 'DESC' THEN p.current_stock
        END DESC,

        CASE 
            WHEN sortColumn = 'status' AND sortOrder = 'ASC' THEN p.status
        END ASC,
        CASE 
            WHEN sortColumn = 'status' AND sortOrder = 'DESC' THEN p.status
        END DESC,
        
        CASE 
            WHEN sortColumn = 'code' AND sortOrder = 'ASC' THEN p.code
        END ASC,
		CASE 
            WHEN sortColumn = 'code' AND sortOrder = 'DESC' THEN p.code
        END DESC,
         CASE 
            WHEN sortColumn = 'taxRate' AND sortOrder = 'ASC' THEN p.tax_rate
        END ASC,
		CASE 
            WHEN sortColumn = 'taxRate' AND sortOrder = 'DESC' THEN p.tax_rate
        END DESC,
        

        /* Default sorting */
        p.created DESC

    LIMIT startRow, rowsPerPage;
END
END$$

DELIMITER ;
;






--Calling query
CALL getProductsList
(
    1,               --tenantId           
    1,               --organizationId
    'Product',       -- searchText
     1,              -- offsetStart (page number)
     10              -- rowsPerPage
    'sku',           -- sortColumn
    'ASC'            -- sortOrder
);








