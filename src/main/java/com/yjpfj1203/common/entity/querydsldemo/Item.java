package com.yjpfj1203.common.entity.querydsldemo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 子订单，每个商品、购买数量、实付价格等
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@NamedEntityGraphs({
        //加载Trade时，同时加载出所有item，promotion及item下面的promotion
        @NamedEntityGraph(name = "ItemNEG",
                attributeNodes = {
                        @NamedAttributeNode(value = "trade"),
                        @NamedAttributeNode(value = "promotionSet")
                })
})
public class Item {
    public static final String ITEM_TRADE = "ItemNEG";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trade trade;
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
    private Integer itemNum;
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Promotion> promotionSet;
}
