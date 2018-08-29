package com.yjpfj1203.common.entity.querydsldemo.repository;

import com.yjpfj1203.common.entity.querydsldemo.repository.query.TradeQueryCondition;
import com.yjpfj1203.common.model.trade.TradeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TradeRepositoryCustom {
    Page<TradeModel> findTradeList(TradeQueryCondition condition, Pageable pageable);
}
