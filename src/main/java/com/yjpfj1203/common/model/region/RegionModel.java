package com.yjpfj1203.common.model.region;

import com.yjpfj1203.common.entity.Region;
import com.yjpfj1203.common.enums.RegionTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Steven on 2016-07-14.
 * 查询region，并给出子regions
 * 例如查询杭州
 * 给出 滨江区，西湖区，下城区。。。。。。
 */
@Getter
@Setter
@NoArgsConstructor
public class RegionModel {
    private Long id;
    private String code;
    private String name;
    private RegionTypeEnum type;
    private List<RegionModel> regions = new ArrayList<>();
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long parentId;

    public RegionModel(Region region) {
        this.id = region.getId();
        this.code = region.getCode();
        this.name = region.getName();
        this.type = region.getType();
        this.parentId = region.getParentId();
        this.latitude = region.getLatitude();
        this.longitude = region.getLongitude();
    }

    public void addChildRegion(RegionModel child){
        getRegions().add(child);
    }

    public void addChildren(List<RegionModel> children){
        getRegions().addAll(children);
    }
}
