package com.billing.dto;

public class ProductPaginationRequest 
{
    private String tenantId;
    private String organizationId;
    private Integer offsetStart = 1; // default 1
    private Integer rowsPerPage = 10; // default 10
    private String searchText = "";  // default empty
    private String sortOrder="";
    private String sortColumn="";


    public ProductPaginationRequest() {}

    // Getters and Setters
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getOrganizationId() { return organizationId; }
    public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

    public Integer getOffsetStart() { return offsetStart; }
    public void setOffsetStart(Integer offsetStart) { this.offsetStart = offsetStart; }

    public Integer getRowsPerPage() { return rowsPerPage; }
    public void setRowsPerPage(Integer rowsPerPage) { this.rowsPerPage = rowsPerPage; }

    public String getSearchText() { return searchText; }
    public void setSearchText(String searchText) { this.searchText = searchText; }

    public String getSortOrder() { return sortOrder; }
    public void setSortOrder(String sortOrder) { this.sortOrder = sortOrder; }

    public String getSortColumn() { return sortColumn; }
    public void setSortColumn(String sortColumn) { this.sortColumn = sortColumn; }
}
