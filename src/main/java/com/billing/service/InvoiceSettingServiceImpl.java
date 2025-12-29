package com.billing.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billing.dto.InvoiceSettingDTO;
import com.billing.interfaces.InvoiceSettingService;
import com.billing.mapper.InvoiceSettingMapper;
import com.billing.models.InvoiceSetting;
import com.billing.repository.InvoiceSettingRepository;
import com.billing.validation.InvoiceSettingValidation;

@Service
@Transactional
public class InvoiceSettingServiceImpl implements InvoiceSettingService {

    private final InvoiceSettingRepository repository;

    public InvoiceSettingServiceImpl(InvoiceSettingRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Override
    public InvoiceSettingDTO create(InvoiceSettingDTO dto) {

        InvoiceSettingValidation.validate(dto);

        InvoiceSetting entity = InvoiceSettingMapper.toEntity(dto);
        entity = repository.save(entity);

        return InvoiceSettingMapper.toDTO(entity);
    }

    // UPDATE
    @Override
    public InvoiceSettingDTO update(Long id, InvoiceSettingDTO dto) {

        InvoiceSetting existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice setting not found"));

        // Preserve createdAt
        LocalDateTime createdAt = existing.getCreatedAt();

        existing.setInvoicePrefix(dto.getInvoicePrefix());
        existing.setInvoiceNumber(dto.getInvoiceNumber());
        existing.setPaymentTerms(dto.getPaymentTerms());
        existing.setCurrencyId(dto.getCurrencyId());
        existing.setNotes(dto.getNotes());
        existing.setIsShowLogo(dto.getIsShowLogo());
        existing.setIsAutoInvoice(dto.getIsAutoInvoice());
        existing.setCreatedAt(createdAt);

        return InvoiceSettingMapper.toDTO(repository.save(existing));
    }

    // GET BY ID
    @Override
    public InvoiceSettingDTO getById(Long id) {

        InvoiceSetting entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice setting not found"));

        return InvoiceSettingMapper.toDTO(entity);
    }
}
