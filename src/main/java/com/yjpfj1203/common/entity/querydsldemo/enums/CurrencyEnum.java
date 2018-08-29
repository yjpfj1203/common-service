package com.yjpfj1203.common.entity.querydsldemo.enums;

public enum CurrencyEnum {
    DOLLAR("美元"), RMB("人民币"), POUND("英磅"), ROUBLE("卢布"), YEN("日元");

    private String description;

    CurrencyEnum(String description){
        this.description = description;
    }
}
