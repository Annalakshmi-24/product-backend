DROP PROCEDURE IF EXISTS product_getall;

CREATE PROCEDURE product_getall()
BEGIN
    SELECT * FROM product_table WHERE is_active = 1;
END;
