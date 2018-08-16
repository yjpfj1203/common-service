package com.yjpfj1203.common.entity.repository;

import com.yjpfj1203.common.entity.Region;
import com.yjpfj1203.common.enums.RegionTypeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 2017/6/21.
 */
@Repository
public class RegionRepositoryImpl implements RegionRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Region> queryByKeyword(String keyword) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Region> query = builder.createQuery(Region.class);
        Root<Region> regionRoot = query.from(Region.class);

        regionRoot.fetch("parent", JoinType.LEFT)
                .fetch("parent", JoinType.LEFT)
                .fetch("parent", JoinType.LEFT)
                .fetch("parent", JoinType.LEFT);
        regionRoot.fetch("regions", JoinType.LEFT)
                .fetch("regions", JoinType.LEFT)
                .fetch("regions", JoinType.LEFT)
                .fetch("regions", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(builder.equal(regionRoot.get("type"), 0));
        predicates.add(builder.like(regionRoot.get("name"), "%" + keyword + "%"));
        query.where(predicates.toArray(new Predicate[]{}));
        query.distinct(true);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Region> queryRegionTree() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Region> query = builder.createQuery(Region.class);
        Root<Region> regionRoot = query.from(Region.class);

        Fetch<Region, List<Region>> cityFetch = regionRoot.fetch("regions", JoinType.LEFT);
        Fetch<Region, List<Region>> areaFetch = cityFetch.fetch("regions", JoinType.LEFT);
        Fetch<Region, List<Region>> blockFetch = areaFetch.fetch("regions", JoinType.LEFT);
        Fetch<Region, List<Region>> whatFetch = blockFetch.fetch("regions", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(regionRoot.get("type"), RegionTypeEnum.PROVINCE));
        predicates.add(builder.isTrue(regionRoot.get("active")));
        query.where(predicates.toArray(new Predicate[]{}));
        query.distinct(true);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Region> queryRegionsByTypeIn(List<RegionTypeEnum> types) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Region> query = builder.createQuery(Region.class);
        Root<Region> regionRoot = query.from(Region.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.in(regionRoot.get("type")).value(types));
        query.where(predicates.toArray(new Predicate[]{}));
        query.distinct(true);
        return em.createQuery(query).getResultList();
    }
}
