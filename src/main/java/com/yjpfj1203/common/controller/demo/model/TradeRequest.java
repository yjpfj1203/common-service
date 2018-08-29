package com.yjpfj1203.common.controller.demo.model;

import com.yjpfj1203.common.entity.querydsldemo.enums.TradeOriginEnum;
import com.yjpfj1203.common.model.trade.ItemModel;
import com.yjpfj1203.common.model.trade.PromotionModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class TradeRequest {
    /**
     * 外部订单编号(外部id)
     */
    private String outTradeNo;
    /**
     * 订单的来源系统
     */
    private TradeOriginEnum originEnum;
    /**
     * 应收总计
     */
    private BigDecimal receivedAmount;
    /**
     * 实收总计
     */
    private BigDecimal receiptAmount;
    /**
     * 优惠总计
     */
    private BigDecimal promotionAmount;
    /**
     * 子订单
     */
    private Set<ItemModel> itemSet;
    /**
     * 订单优惠
     * orphanRemoval: 删除孤儿元素
     */
    private Set<PromotionModel> promotionSet;
}
