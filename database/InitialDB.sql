-- Create the products table
CREATE DATABASE products;
CREATE TABLE product_table (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sku VARCHAR(50),
    product_name VARCHAR(100),
    tenant_id INT,
    organization_id INT,
    status VARCHAR(50),
    created DATETIME,
    created_by DATETIME,
    modified DATETIME,
    modified_by VARCHAR(50),
    is_active TINYINT,
    category VARCHAR(255),
    stock INT,
    purchase_price INT,
    sales_price INT
);

