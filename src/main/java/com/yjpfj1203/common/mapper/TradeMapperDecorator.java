package com.yjpfj1203.common.mapper;

import com.yjpfj1203.common.controller.demo.model.TradeRequest;
import com.yjpfj1203.common.entity.querydsldemo.Trade;

import java.util.Optional;

public abstract class TradeMapperDecorator implements TradeMapper {
    private final TradeMapper delegate;

    public TradeMapperDecorator(TradeMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public Trade requestToDo(TradeRequest tradeRequest){
        Trade trade = this.delegate.requestToDo(tradeRequest);
        //设置item的trade属性值
        Optional.ofNullable(trade.getItemSet()).ifPresent(itemSet -> itemSet.forEach(item -> {
            item.setTrade(trade);
        }));
        return trade;
    }
}
