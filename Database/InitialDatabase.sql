-- Create DataBase
CREATE DATABASE azairebilling;

--Create Table
CREATE TABLE product_table (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sku VARCHAR(50),
    product_name VARCHAR(100),
    tenant_id VARCHAR(50),
    organization_id VARCHAR(50),
    status VARCHAR(50),
    created DATETIME,
    created_by DATETIME,
    modified DATETIME,
    modified_by VARCHAR(50),
    active TINYINT,
    category_id INT,
    current_stock INT,
    minimum_stock INT,
    purchase_price INT,
    sales_price INT,
    unit VARCHAR(50),
    tax_details VARCHAR(50)
);


--For creating category_id as foreign key:
CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    organisation_id VARCHAR(255) NULL,
    tenant_id VARCHAR(255) NULL,
    category_code VARCHAR(255) NULL,
    description VARCHAR(255) NULL,
    status TINYINT DEFAULT 1,
    created DATETIME(6) NULL,
    created_by VARCHAR(255) NULL,
    modified DATETIME(6) NULL,
    modified_by VARCHAR(255) NULL,
    type VARCHAR(255) NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE;
);



--Make category_id as FK
ALTER TABLE product_table
ADD CONSTRAINT fk_category
FOREIGN KEY (category_id) REFERENCES category(category_id);


-- --For creating entity_type_id as foreign key
-- Create table master_data(
--     id INT PRIMARY KEY AUTO_INCREMENT,
--     entity_type_id  INT NOT NULL,
--     tenant_id VARCHAR(50) NOT NULL,
--     organization_id VARCHAR(50) NOT NULL,
--     description VARCHAR(50) NOT NULL,
--     sort_order int 
-- )