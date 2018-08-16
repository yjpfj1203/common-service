package com.yjpfj1203.common.model.region;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by rk on 2016-07-14.
 * 查询region，并给出子regions
 * 例如查询杭州
 * 给出 滨江区，西湖区，下城区。。。。。。,再加上商圈
 */
@Getter
@Setter
@NoArgsConstructor
public class RegionBlockModel {
    private Long blockId;
    private Long areaId;
    private Long cityId;
    private Long provinceId;
    private String blockName;
    private String areaName;
    private String cityName;
    private String provinceName;

    public RegionBlockModel(RegionTreeModel model) {
        setProvinceId(model.getId());
        setProvinceName(model.getName());

        if (null == model.getRegion())
            return;
        setCityId(model.getRegion().getId());
        setCityName(model.getRegion().getName());

        if (null == model.getRegion().getRegion())
            return;
        setAreaId(model.getRegion().getRegion().getId());
        setAreaName(model.getRegion().getRegion().getName());

        if (null == model.getRegion().getRegion().getRegion())
            return;
        setBlockId(model.getRegion().getRegion().getRegion().getId());
        setBlockName(model.getRegion().getRegion().getRegion().getName());
    }
}
