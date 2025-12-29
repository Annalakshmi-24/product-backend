package com.billing.interfaces;

import com.billing.dto.InvoiceSettingDTO;

public interface InvoiceSettingService 
{

    InvoiceSettingDTO create(InvoiceSettingDTO dto);

    InvoiceSettingDTO update(Long id, InvoiceSettingDTO dto);

    InvoiceSettingDTO getById(Long id);
}
