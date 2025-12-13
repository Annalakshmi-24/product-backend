package com.billing.repository;

import com.billing.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> 
{

   //GetAllProductList
    @Query(
        value = "CALL getProductsList(:tenantId, :organizationId, :searchText, :offsetStart, :rowsPerPage)",
        nativeQuery = true
    )
    List<Object[]> getAllProductList(
            @Param("tenantId") String tenantId,
            @Param("organizationId") String organizationId,
            @Param("searchText") String searchText,
            @Param("offsetStart") Integer offsetStart,
            @Param("rowsPerPage") Integer rowsPerPage
    );

   
   // FETCH METHODS
   
        List<Product> findByTenantIdAndOrganizationId(String tenantId, String organizationId);

        List<Product> findByTenantIdAndOrganizationIdAndIsActive(String tenantId, String organizationId, Integer is_active);

        List<Product> findByTenantIdAndIsActive(String tenantId, Integer is_active);

        List<Product> findByOrganizationIdAndIsActive( String organizationId, Integer is_active);

        List<Product> findByIsActive(Integer is_active);

   // GET PRODUCT BY ID
   
        Product findByTenantIdAndOrganizationIdAndId(String tenantId, String organizationId, Long id);

   //DELETE BY ID
 
        Product findByTenantIdAndOrganizationIdAndIdAndIsActive(String tenantId, String organizationId, Long id, Integer isActive);
   
}


