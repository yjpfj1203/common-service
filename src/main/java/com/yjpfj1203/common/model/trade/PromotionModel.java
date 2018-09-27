package com.yjpfj1203.common.model.trade;

import com.yjpfj1203.common.entity.querydsldemo.Promotion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@NoArgsConstructor
public class PromotionModel {
    private Long id;
    /**
     * 优惠名称
     */
    private String name;
    /**
     * 优惠金额
     */
    private BigDecimal amount;

    public PromotionModel(String name, BigDecimal amount){
        setName(name);
        setAmount(amount);
    }

//    public PromotionModel(Promotion promotion){
//        setId(promotion.getId());
//        setName(promotion.getName());
//        setAmount(promotion.getAmount());
//    }
}
