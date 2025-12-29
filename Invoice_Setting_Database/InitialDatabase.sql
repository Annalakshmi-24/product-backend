//Invoice_setting table creation:

CREATE TABLE invoice_setting (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tenant_id VARCHAR(50),
    organization_id VARCHAR(50),
    invoice_prefix VARCHAR(255),
    invoice_number INT,
    payment_terms INT,
    currency_id VARCHAR(10),
    notes TEXT,
    is_show_logo BOOLEAN DEFAULT TRUE,
    is_auto_invoice BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_currency_id
    FOREIGN KEY (currency_id)
    REFERENCES master_table(currency_id)
);

//Sample insert query with values:

//Insert query:

INSERT INTO invoice_setting (
    tenant_id,
    organization_id,
    invoice_prefix,
    invoice_number,
    payment_terms,
    currency_id,
    notes,
    is_show_logo,
    is_auto_invoice
)
VALUES (
    1,
    1,
    'Azaire Business Solutions',
    1046,
    30,
    'INR',
    'Thank you for your business!',
    TRUE,
    TRUE
);