package com.yjpfj1203.common.entity.querydsldemo.repository;

import com.yjpfj1203.common.entity.querydsldemo.Trade;
import com.yjpfj1203.common.entity.querydsldemo.UserDemo;
import com.yjpfj1203.common.entity.querydsldemo.enums.CurrencyEnum;
import com.yjpfj1203.common.entity.querydsldemo.repository.result.LongAndStringModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 使用自定义query时，不建议使用占位符，不方便代码阅读
 * @author yjpfj1203
 */
@Repository
public interface UserDemoRepository extends JpaRepository<UserDemo, Long>{

    /**
     * EntityGraph 对于这种自定义的query也是适用的
     * 如果不加entityGraph，会产生N+1问题
     *
     * @param currencySet
     * @return
     */
    @EntityGraph(value = UserDemo.USER_DEMO_NEG, type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "select u from UserDemo u " +
            "left join u.currencySet cst " +  //注意此处的写法
            "where cst in :currSet")
    List<UserDemo> findByCurrency(@Param(value = "currSet") Set<CurrencyEnum> currencySet);

    /**
     * EntityGraph 对于这种自定义的query也是适用的
     * 注：这里必须用接口来接收返回值，当然，也可以用map接收返回值
     * List<Map> findByName(@Param(value = "name") String name);
     * @param name
     * @return
     */
    @Query(value = "select u.id as longData, u.name as stringData from UserDemo u " +
            "where u.name like %:name%")
    List<LongAndStringModel> findByName(@Param(value = "name") String name);
}
