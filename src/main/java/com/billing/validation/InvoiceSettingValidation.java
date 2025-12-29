package com.billing.validation;

import com.billing.dto.InvoiceSettingDTO;

public class InvoiceSettingValidation {

    public static void validate(InvoiceSettingDTO dto) {
        if (dto.getCurrencyId() == null || dto.getCurrencyId().isEmpty()) {
            throw new RuntimeException("Currency ID is required");
        }
        if (dto.getInvoiceNumber() == null) {
            throw new RuntimeException("Invoice number is required");
        }
    }
}
