package com.yjpfj1203.common.controller.demo;

import com.yjpfj1203.common.controller.demo.model.TradeRequest;
import com.yjpfj1203.common.entity.querydsldemo.UserDemo;
import com.yjpfj1203.common.entity.querydsldemo.enums.CurrencyEnum;
import com.yjpfj1203.common.entity.querydsldemo.repository.UserDemoRepository;
import com.yjpfj1203.common.entity.querydsldemo.repository.query.TradeQueryCondition;
import com.yjpfj1203.common.entity.querydsldemo.repository.result.LongAndStringModel;
import com.yjpfj1203.common.model.PageResponse;
import com.yjpfj1203.common.model.trade.TradeModel;
import com.yjpfj1203.common.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yjpfj1203
 * 区域选择服务
 */
@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserDemoController {
    @Autowired
    private UserDemoRepository userDemoRepository;

    @GetMapping
    public List<UserDemo> findAll(){
        return userDemoRepository.findAll();
    }

    @GetMapping(value = "/currencies")
    public List<UserDemo> findByCurrencyList(@RequestParam(value = "currencies") CurrencyEnum[] currencies){
        return userDemoRepository.findByCurrency(Arrays.asList(currencies).stream().collect(Collectors.toSet()));
    }

    @GetMapping(value = "/keyword")
    public List<LongAndStringModel> findByName(@RequestParam(value = "name", required = false) String name){
        return userDemoRepository.findByName(name);
    }
}
