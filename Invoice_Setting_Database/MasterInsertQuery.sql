//Master table creation:

CREATE TABLE master_table (
    currency_id VARCHAR(10) PRIMARY KEY,
    currency_name VARCHAR(100) NOT NULL,
    currency_symbol VARCHAR(10),
   
);





//Insert values to master table:

INSERT INTO currency_master
(currency_id, currency_name, currency_symbol)
VALUES
('INR', 'Indian Rupee', '₹'),
('USD', 'US Dollar', '$'),
('EUR', 'Euro', '€'),
('GBP', 'British Pound', '£');