package com.yjpfj1203.common;

import com.google.gson.Gson;
import com.yjpfj1203.common.controller.demo.model.TradeRequest;
import com.yjpfj1203.common.entity.querydsldemo.enums.TradeOriginEnum;
import com.yjpfj1203.common.model.trade.ItemModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MainTest {
    public static void main(String[] args){
        TradeRequest trade = new TradeRequest();
        trade.setOriginEnum(TradeOriginEnum.TAOBAO);
        trade.setOutTradeNo("TB-180823-08989883124");
        trade.setPromotionAmount(BigDecimal.valueOf(43.90));
        trade.setReceiptAmount(BigDecimal.valueOf(2700.00));
        trade.setReceivedAmount(BigDecimal.valueOf(2743.90));
        trade.setItemSet(Arrays.asList(1,2).stream().map(i ->{
            ItemModel item = new ItemModel();
            item.setItemName("游泳圈");
            item.setItemNum(2);
            item.setOutOrderCode("o-325498783524");
            item.setPrice(BigDecimal.valueOf(111.00));
            item.setPromotionAmount(BigDecimal.ZERO);
            item.setPromotionSet(new HashSet<>());
            item.setReceivedAmount(BigDecimal.valueOf(222.00));
            return item;
        }).collect(Collectors.toSet()));
        trade.setPromotionSet(new HashSet<>());
        System.out.println(new Gson().toJson(trade));
    }
}
