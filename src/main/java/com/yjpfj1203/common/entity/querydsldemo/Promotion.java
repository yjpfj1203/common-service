package com.yjpfj1203.common.entity.querydsldemo;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 优惠名称
     */
    private String name;
    /**
     * 优惠金额
     */
    private BigDecimal amount;
}
