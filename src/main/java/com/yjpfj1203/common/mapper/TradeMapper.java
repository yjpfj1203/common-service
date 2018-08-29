package com.yjpfj1203.common.mapper;

import com.yjpfj1203.common.controller.demo.model.TradeRequest;
import com.yjpfj1203.common.entity.querydsldemo.Item;
import com.yjpfj1203.common.entity.querydsldemo.Trade;
import com.yjpfj1203.common.model.trade.ItemModel;
import com.yjpfj1203.common.model.trade.TradeModel;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

//@Mapper(componentModel = "spring")
@Mapper
@DecoratedWith(TradeMapperDecorator.class)
//mapstruct官网地址：http://mapstruct.org/documentation/dev/reference/html/
public interface TradeMapper
{
    TradeMapper MAPPER = Mappers.getMapper(TradeMapper.class);

    @Mappings({
            @Mapping(target = "outTradeNo", source = "outTradeNo"), //当属性名不同时，可以设置映射关系
            @Mapping(target = "promotionAmount", ignore = true)
    })
    TradeModel DoToModel(Trade trade);

    ItemModel itemDoToModel(Item item);

    Trade requestToDo(TradeRequest tradeRequest);
}