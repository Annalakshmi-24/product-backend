
package com.billing.dto;

public class ProductIdNameDTO 
{

    private Long id;
    private String productName;

    public ProductIdNameDTO(Long id, String productName) 
    {
        this.id = id;
        this.productName = productName;
    }

    public Long getId() 
    {
        return id;
    }

    public String getProductName() 
    {
        return productName;
    }
}
