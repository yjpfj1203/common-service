package com.yjpfj1203.common.entity.querydsldemo.repository.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.yjpfj1203.common.entity.querydsldemo.QItem;
import com.yjpfj1203.common.entity.querydsldemo.QPromotion;
import com.yjpfj1203.common.entity.querydsldemo.QTrade;
import com.yjpfj1203.common.entity.querydsldemo.enums.TradeOriginEnum;
import com.yjpfj1203.common.entity.querydsldemo.enums.TradeStatusEnum;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TradeQueryCondition {

    private String outTradeNo;
    private TradeOriginEnum originEnum;
    private List<TradeStatusEnum> statuses;
    private String keyword;
    private BigDecimal minReceiptAmount;
    private BigDecimal maxReceiptAmount;
    private Date minCreatedDate;
    private Date maxCreatedDate;

    public BooleanBuilder tradeListExpression() {
        final QTrade trade = QTrade.trade;
        final QItem item = QItem.item;
        final QPromotion promotion = QPromotion.promotion;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(trade.invalid.eq(false));
        if (CollectionUtils.isNotEmpty(statuses)){
            builder.and(trade.status.in(statuses));
        }
        if (StringUtils.isNotBlank(outTradeNo)){
            builder.and(trade.outTradeNo.eq(outTradeNo));
        }
        if (originEnum != null){
            builder.and(trade.originEnum.eq(originEnum));
        }
        if (StringUtils.isNotBlank(keyword)){
            builder.and(Expressions.asString(trade.outTradeNo)
                    .append(Expressions.asString("_"))
                    .append(item.itemName)
                    .append(Expressions.asString("_"))
                    .append(promotion.name)
                    .contains(keyword));
        }
        if (minReceiptAmount != null || maxReceiptAmount != null) {
            builder.and(trade.receiptAmount.between(minReceiptAmount == null ? BigDecimal.ZERO : minReceiptAmount, maxReceiptAmount == null ? BigDecimal.valueOf(Double.MAX_VALUE) : maxReceiptAmount));
        }

        if (minCreatedDate != null || maxCreatedDate != null){
            builder.and(trade.createdDate.between(minCreatedDate == null? new Date(0L): minCreatedDate, maxCreatedDate == null? DateUtils.addYears(new Date(), 1000): maxCreatedDate));
        }
        return builder;
    }
}
