package com.billing.controller;

import org.springframework.web.bind.annotation.*;
import com.billing.dto.InvoiceSettingDTO;
import com.billing.interfaces.InvoiceSettingService;

@RestController
@RequestMapping("/api/invoice-setting")
public class InvoiceSettingController 
{

    private final InvoiceSettingService invoiceService;

    public InvoiceSettingController(InvoiceSettingService invoiceService) 
    {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/createInvoiceSetting")
    public InvoiceSettingDTO create(@RequestBody InvoiceSettingDTO dto) 
    {
        return invoiceService.create(dto);
    }

    @PutMapping("/updateInvoiceSetting/{id}")
    public InvoiceSettingDTO update(@PathVariable Long id, @RequestBody InvoiceSettingDTO dto) 
    {
        return invoiceService.update(id, dto);
    }

    @GetMapping("/getByIdInvoiceSetting/{id}")
    public InvoiceSettingDTO getById(@PathVariable Long id) 
    {
        return invoiceService.getById(id);
    }
}
