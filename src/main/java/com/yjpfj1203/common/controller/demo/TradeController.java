package com.yjpfj1203.common.controller.demo;

import com.yjpfj1203.common.controller.demo.model.TradeRequest;
import com.yjpfj1203.common.entity.querydsldemo.repository.query.TradeQueryCondition;
import com.yjpfj1203.common.model.PageResponse;
import com.yjpfj1203.common.model.trade.TradeModel;
import com.yjpfj1203.common.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author yjpfj1203
 * 区域选择服务
 */
@RestController
@RequestMapping(value = "/trades")
@Slf4j
public class TradeController {

    @Autowired
    private TradeService tradeService;

    /**
     * 所有区域信息（树状结构）
     * @return 系统所有地区关系
     */
    @PostMapping
    public Long saveTrade(@RequestBody TradeRequest request) {
        return tradeService.save(request);
    }

    /**
     * 所有区域信息（树状结构）
     * @return 系统所有地区关系
     */
    @GetMapping
    public PageResponse<TradeModel> queryTrades(@PageableDefault(value = 20, sort = {"lastModifiedDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return tradeService.findTrades(pageable);
    }

    /**
     * 所有区域信息（树状结构）
     * @return 系统所有地区关系
     */
    @GetMapping(value = "dsl")
    public PageResponse<TradeModel> queryTradesDsl(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "startTime", required = false) Date startTime,
            @RequestParam(value = "endTime", required = false) Date endTime,
            @PageableDefault(value = 20, sort = {"lastModifiedDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        TradeQueryCondition condition = TradeQueryCondition.builder()
                .keyword(keyword)
                .minCreatedDate(startTime)
                .maxCreatedDate(endTime)
                .build();

        return tradeService.findTradesDsl(condition, pageable);


    }

}
