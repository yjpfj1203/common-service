package com.yjpfj1203.common.model.trade;

import com.yjpfj1203.common.entity.querydsldemo.Trade;
import com.yjpfj1203.common.entity.querydsldemo.enums.TradeOriginEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class TradeModel {
    private Long id;
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
     */
    private Set<PromotionModel> promotionSet;

    private Date createdDate;
    private Date lastModifiedDate;

//    public TradeModel(Trade trade){
//        setId(trade.getId());
//        setOutTractNo(trade.getOutTractNo());
//        setOriginEnum(trade.getOriginEnum());
//        setReceivedAmount(trade.getReceivedAmount());
//        setReceiptAmount(trade.getReceiptAmount());
//        setPromotionAmount(trade.getPromotionAmount());
//        setItemSet(
//                trade.getItemSet().stream().map(ItemModel::new).collect(Collectors.toSet())
//        );
//        setPromotionSet(
//                trade.getPromotionSet().stream().map(PromotionModel::new).collect(Collectors.toSet())
//        );
//        setCreatedDate(trade.getCreatedDate());
//        setLastModifiedDate(trade.getLastModifiedDate());
//    }
    public BigDecimal getReceivedAmount(){
        return (receivedAmount == null? BigDecimal.ZERO: receivedAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getReceiptAmount(){
        return (receiptAmount == null? BigDecimal.ZERO: receiptAmount).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPromotionAmount(){
        return (promotionAmount == null? BigDecimal.ZERO: promotionAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
