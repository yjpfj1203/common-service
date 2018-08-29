package com.yjpfj1203.common.entity.querydsldemo.repository;

import com.yjpfj1203.common.entity.querydsldemo.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author yjpfj1203
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Long>, TradeRepositoryCustom{
    /**
     * 为了避免N+1问题，尽量每个查询都使用EntityGraph
     * @param pageable
     * @return
     */
    @EntityGraph(value = Trade.Trade_ALL_FIELD, type = EntityGraph.EntityGraphType.FETCH)
    Page<Trade> findAll(Pageable pageable);
}
