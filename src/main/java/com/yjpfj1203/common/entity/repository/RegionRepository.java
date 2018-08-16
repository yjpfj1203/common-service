package com.yjpfj1203.common.entity.repository;

import com.yjpfj1203.common.entity.Region;
import com.yjpfj1203.common.enums.RegionTypeEnum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Steven on 2016-07-14.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, RegionRepositoryCustom {

    @EntityGraph(value = "Region.subRegion", type = EntityGraph.EntityGraphType.LOAD)
    List<Region> findDistinctByIdIn(Collection<Long> ids);

    @EntityGraph(value = "subRegion", type = EntityGraph.EntityGraphType.LOAD)
    List<Region> queryDistinctByIdIn(Collection<Long> ids);

    @EntityGraph(value = "Region.subRegion", type = EntityGraph.EntityGraphType.LOAD)
    List<Region> findDistinctByParentIsNull();

    @EntityGraph(value = "Region.subRegion", type = EntityGraph.EntityGraphType.LOAD)
    List<Region> findDistinctByParentId(Long parentId);

    @EntityGraph(value = "Region.subRegion", type = EntityGraph.EntityGraphType.LOAD)
    List<Region> findDistinctByParentIdIn(List<Long> parentIds);

    @EntityGraph(value = "Region.subRegion", type = EntityGraph.EntityGraphType.LOAD)
    Region queryById(Long id);

    List<Region> findByTypeIn(List<RegionTypeEnum> types);

    List<Region> findByParentIdInAndType(List<Long> parentId, RegionTypeEnum type);

    List<Region> findByParentIdIn(Collection<Long> parentId);

}
