

package com.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.billing.models.MasterData;

public interface MasterDataRepository extends JpaRepository<MasterData, Long>
 {
 }
