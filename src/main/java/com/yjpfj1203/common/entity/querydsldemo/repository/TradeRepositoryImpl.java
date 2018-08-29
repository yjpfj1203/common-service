package com.yjpfj1203.common.entity.querydsldemo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yjpfj1203.common.entity.querydsldemo.Item;
import com.yjpfj1203.common.entity.querydsldemo.QItem;
import com.yjpfj1203.common.entity.querydsldemo.QPromotion;
import com.yjpfj1203.common.entity.querydsldemo.QTrade;
import com.yjpfj1203.common.entity.querydsldemo.repository.query.TradeQueryCondition;
import com.yjpfj1203.common.mapper.TradeMapper;
import com.yjpfj1203.common.model.trade.TradeModel;
import com.yjpfj1203.common.util.DSLHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yjpfj1203
 */
public class TradeRepositoryImpl implements TradeRepositoryCustom{

    @Autowired
    private EntityManager em;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     * 这个比较适合做列表查询，不适合做有list属性的对象查询。
     * 目前没找到下面包含list的查询方式，如果需要，就全部查出来，自己封装好了。
     * 或是查询主对象，再进行二次查询
     * @param condition
     * @param pageable
     * @return
     */
    @Override
    public Page<TradeModel> findTradeList(TradeQueryCondition condition, Pageable pageable) {
        QTrade trade = QTrade.trade;
        QItem item = QItem.item;
        QPromotion promotion = QPromotion.promotion;

        JPAQuery<TradeModel> query = jpaQueryFactory
                .from(trade)
                .leftJoin(trade.itemSet, item)
                .leftJoin(trade.promotionSet, promotion)
                .leftJoin(item.promotionSet, promotion)
                .select(Projections.bean(TradeModel.class,
                        trade.id.as("id"),
                        trade.outTradeNo,
                        trade.status,
                        trade.receiptAmount))
                .where(condition.tradeListExpression())
                .groupBy(trade.id)
                .orderBy(trade.lastModifiedDate.desc());
        Page<TradeModel> page = DSLHelper.applyPage(query, pageable);

        //以下是二次查询，查询订单下的item
        EntityGraph itemEntityGraph = em.getEntityGraph(Item.ITEM_TRADE);
        Set<Long> tradeIdSet = page.getContent().stream().map(TradeModel::getId).collect(Collectors.toSet());
        JPAQuery<Item> itemQuery = jpaQueryFactory
                .selectFrom(item)
                .leftJoin(item.trade, trade)
                .setHint("javax.persistence.fetchgraph", itemEntityGraph)
                .where(trade.id.in(tradeIdSet));

        Map<Long, List<Item>> itemMap = itemQuery.fetch()
                .stream()
                .collect(Collectors.groupingBy(o -> o.getTrade().getId()));
        page.getContent().forEach(t -> {
            t.setItemSet(itemMap.get(t.getId()).stream().map(it -> TradeMapper.MAPPER.itemDoToModel(it)).collect(Collectors.toSet()));
        });
        return page;
    }
}
