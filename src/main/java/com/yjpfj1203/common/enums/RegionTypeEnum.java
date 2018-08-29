package com.yjpfj1203.common.enums;

/**
 * Created by Steven on 2017/6/21.
 */
public enum RegionTypeEnum {

    PROVINCE("省"),
    CITY("市"),
    AREA("区域"),
    STREET("街道/商圈");

    private String desc;

    RegionTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public static RegionTypeEnum getEnum(String value) {
        for(RegionTypeEnum v : values())
            if(v.getDesc().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
