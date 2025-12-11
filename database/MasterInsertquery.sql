-- Query to insert a new product with meta fields
INSERT INTO products (
    sku,
    product_id,
    tenant_id,
    organizational_id,
    
    status,
    
    created_by,
    modified_by,
    is_active,
    category,
    stock,
    purchase_price,
    sales_price
)
VALUES (
    'SKU001',       -- sku
    101,            -- product_id
    1,              -- tenant_id
    1,              -- organizational_id
    'Available',    -- status
    'Admin',        -- created_by
    'Admin',        -- modified_by
    1,              -- is_active (1 = true, 0 = false)
    'Electronics',  -- category
    50,             -- stock
    500,            -- purchase_price
    750             -- sales_price
);