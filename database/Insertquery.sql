-- Query to insert a new product 
INSERT INTO products (
    sku,
    product_id,
    tenant_id,
    organizational_id,
    status,
    created_by,
    modified_by,
    is_active,
    category_id,
    current_stock,
    minimum_stock,
    purchase_price,
    sales_price,
    code,
    unit,
    tax_rate
)
VALUES 
(
    'SKU001',       -- sku
    101,            -- product_id
    1,              -- tenant_id
    1,              -- organizational_id
    'Available',    -- status
    'Admin',        -- created_by
    'Admin',        -- modified_by
    1,              -- is_active (1 = true, 0 = false)
    1,              -- category_id
    50,             -- current_stock
    50,             --minimum_stock
    500,            -- purchase_price
    750             -- sales_price
    123456          --code
    'pieces',       --unit
    '18% gst'       --tax_rate
    );

-- Query to insert a data in category_table

INSERT INTO category (category_id, category_name, organisation_id, tenant_id, category_code, description, status, created, created_by, modified, modified_by, type, active)
VALUES (1,'Electronics','1', '1',  'ELEC', 'Electronic gadgets ', 1, NOW(6), 'admin', NOW(6), 'admin', 'Goods', TRUE); 