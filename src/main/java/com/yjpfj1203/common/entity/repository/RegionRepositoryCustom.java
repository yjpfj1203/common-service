package com.yjpfj1203.common.entity.repository;

import com.yjpfj1203.common.entity.Region;
import com.yjpfj1203.common.enums.RegionTypeEnum;

import java.util.List;

/**
 * Created by Steven on 2017/6/21.
 */
public interface RegionRepositoryCustom {

    List<Region> queryByKeyword(String keyword);

    List<Region> queryRegionTree();

    List<Region> queryRegionsByTypeIn(List<RegionTypeEnum> types);

}
