package com.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.billing.models.InvoiceSetting;

public interface InvoiceSettingRepository
        extends JpaRepository<InvoiceSetting, Long> 
        {
        }
