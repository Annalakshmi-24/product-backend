package com.billing.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "entity_type")
public class EntityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String tenantId;
    private String organizationId;
    private Boolean active;

    @OneToMany(mappedBy = "entityType")
    private List<MasterData> masterDataList;

    // Getters and Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getOrganizationId() { return organizationId; }
    public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<MasterData> getMasterDataList() { return masterDataList; }
    public void setMasterDataList(List<MasterData> masterDataList) { this.masterDataList = masterDataList; }
}
