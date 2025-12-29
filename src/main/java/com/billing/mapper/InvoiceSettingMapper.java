package com.billing.mapper;

import com.billing.dto.InvoiceSettingDTO;
import com.billing.models.InvoiceSetting;

public class InvoiceSettingMapper {

    public static InvoiceSetting toEntity(InvoiceSettingDTO dto) 
    {
        InvoiceSetting entity = new InvoiceSetting();
        //entity.setTenantId(dto.getTenantId());
        //entity.setOrganizationId(dto.getOrganizationId());
        entity.setInvoicePrefix(dto.getInvoicePrefix());
        entity.setInvoiceNumber(dto.getInvoiceNumber());
        entity.setPaymentTerms(dto.getPaymentTerms());
        entity.setCurrencyId(dto.getCurrencyId());
        entity.setNotes(dto.getNotes());
        entity.setIsShowLogo(dto.getIsShowLogo());
        entity.setIsAutoInvoice(dto.getIsAutoInvoice());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }

    public static InvoiceSettingDTO toDTO(InvoiceSetting entity) 
    {
        InvoiceSettingDTO dto = new InvoiceSettingDTO();
        dto.setId(entity.getId());
        //dto.setTenantId(entity.getTenantId());
        //dto.setOrganzationId(entity.getorganziationId());
        dto.setInvoicePrefix(entity.getInvoicePrefix());
        dto.setInvoiceNumber(entity.getInvoiceNumber());
        dto.setPaymentTerms(entity.getPaymentTerms());
        dto.setCurrencyId(entity.getCurrencyId());
        dto.setNotes(entity.getNotes());
        dto.setIsShowLogo(entity.getIsShowLogo());
        dto.setIsAutoInvoice(entity.getIsAutoInvoice());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
