package com.yjpfj1203.common.entity.repository;

import com.yjpfj1203.common.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yjpfj1203
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{
}
