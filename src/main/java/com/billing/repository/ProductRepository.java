package com.billing.repository;

import com.billing.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // FINDERS
    @Query("SELECT p FROM Product p WHERE p.tenant_id = :tenantId AND p.organization_id = :organizationId")
    List<Product> findByTenantIdAndOrganizationId(@Param("tenantId") Long tenantId, @Param("organizationId") Long organizationId);
    
    @Query("SELECT p FROM Product p WHERE p.tenant_id = :tenantId AND p.organization_id = :organizationId AND p.is_active = :isActive")
    List<Product> findByTenantIdAndOrganizationIdAndIsActive(@Param("tenantId") Long tenantId, @Param("organizationId") Long organizationId, @Param("isActive") Integer isActive);

    @Query("SELECT p FROM Product p WHERE p.is_active = :isActive")
    List<Product> findByIsActive(@Param("isActive") Integer isActive);

    @Query("SELECT p FROM Product p WHERE p.tenant_id = :tenantId AND p.is_active = :isActive")
    List<Product> findByTenantIdAndIsActive(@Param("tenantId") Long tenantId, @Param("isActive") Integer isActive);

    @Query("SELECT p FROM Product p WHERE p.organization_id = :organizationId AND p.is_active = :isActive")
    List<Product> findByOrganizationIdAndIsActive(@Param("organizationId") Long organizationId, @Param("isActive") Integer isActive);

    // STORED PROCEDURE
    @Query(
        value = "CALL getProductsPagination1(:tenantId, :organizationId, :searchText, :offsetStart, :rowsPerPage)",
        nativeQuery = true
    )
    List<Object[]> getProductsPagination(
            @Param("tenantId") Long tenantId,
            @Param("organizationId") Long organizationId,
            @Param("searchText") String searchText,
            @Param("offsetStart") Integer offsetStart,
            @Param("rowsPerPage") Integer rowsPerPage
    );
}
