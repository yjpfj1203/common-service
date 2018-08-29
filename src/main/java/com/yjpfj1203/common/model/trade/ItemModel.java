package com.yjpfj1203.common.model.trade;

import com.yjpfj1203.common.entity.querydsldemo.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;


@Setter
@Getter
@NoArgsConstructor
public class ItemModel {
    private Long id;
    /**
     * 外部子订单编号
     */
    private String outOrderCode;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 购买数量
     */
    private int itemNum;
    /**
     * 单价
     */
    private BigDecimal price;
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
     * 参与的优惠列表
     */
    private Set<PromotionModel> promotionSet;

//    public ItemModel(Item item) {
//        setId(item.getId());
//        setOutOrderCode(item.getOutOrderCode());
//        setItemName(item.getItemName());
//        setItemNum(item.getItemNum());
//        setPrice(item.getPrice());
//        setReceiptAmount(item.getReceiptAmount());
//        setReceivedAmount(item.getReceivedAmount());
//        setPromotionAmount(item.getPromotionAmount());
//        setPromotionSet(item.getPromotionSet().stream().map(PromotionModel::new).collect(Collectors.toSet()));
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
