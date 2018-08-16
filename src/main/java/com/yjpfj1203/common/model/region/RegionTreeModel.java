package com.yjpfj1203.common.model.region;

import com.yjpfj1203.common.entity.Region;
import com.yjpfj1203.common.enums.RegionTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Steven on 2016-08-09.
 */
@Getter
@Setter
@NoArgsConstructor
public class RegionTreeModel {
    private Long id;
    private String code;
    private String name;
    private RegionTreeModel region;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private RegionTypeEnum type;

    public RegionTreeModel(Region region) {
        this.id = region.getId();
        this.code = region.getCode();
        this.name = region.getName();
        this.latitude = region.getLatitude();
        this.longitude = region.getLongitude();
        this.type = region.getType();
    }

}

