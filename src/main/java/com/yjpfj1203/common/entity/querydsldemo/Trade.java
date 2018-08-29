package com.yjpfj1203.common.entity.querydsldemo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yjpfj1203.common.entity.querydsldemo.enums.TradeOriginEnum;
import com.yjpfj1203.common.entity.querydsldemo.enums.TradeStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 一笔交易(主订单)
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
//此配置是将trade以对象作为controller的返回值，在使用Jackson序列化时，避免出现无限循环或递归的问题而导致序列化报错
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@EntityListeners(AuditingEntityListener.class)
@NamedEntityGraphs({
        //加载Trade时，同时加载出所有item，promotion及item下面的promotion
        @NamedEntityGraph(name = "TradeNEG",
                attributeNodes = {
                        @NamedAttributeNode(value = "itemSet", subgraph = "TradeNEG_ItemSet"),
                        @NamedAttributeNode(value = "promotionSet", subgraph = "TradeNEG_PromotionSet"),
                },
                subgraphs = {
                        @NamedSubgraph(name = "TradeNEG_ItemSet",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "promotionSet")
                                }
                        )
                })
})
@Table(name = "trade")
public class Trade {
    public static final String Trade_ALL_FIELD = "TradeNEG";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private TradeStatusEnum status;
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
    //注释说明：在进行Jackson序列化时，忽略item中trade属性。
    @JsonIgnoreProperties(value = {"trade", "hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "trade", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Item> itemSet;
    /**
     * 订单优惠
     * orphanRemoval: 删除孤儿元素
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Promotion> promotionSet;

    private boolean invalid = false;

    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
}
