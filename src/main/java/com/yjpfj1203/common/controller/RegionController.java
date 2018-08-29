package com.yjpfj1203.common.controller;

import com.yjpfj1203.common.entity.Region;
import com.yjpfj1203.common.entity.repository.RegionRepository;
import com.yjpfj1203.common.enums.RegionTypeEnum;
import com.yjpfj1203.common.model.region.RegionModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yjpfj1203
 * 区域选择服务
 */
@RestController
@RequestMapping(value = "/regions")
@Slf4j
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    /**
     * 所有区域信息（树状结构）
     * @return 系统所有地区关系
     */
    @GetMapping
    public List<RegionModel> queryRegions() {
        List<Region> allRegions = regionRepository.findAll();
        Map<RegionTypeEnum, List<RegionModel>> typeRegionMap = allRegions.stream().map(RegionModel::new).collect(Collectors.groupingBy(RegionModel::getType));
        List<RegionModel> provinceList = typeRegionMap.get(RegionTypeEnum.PROVINCE);
        Map<Long, RegionModel> regionMap = new HashMap<>();
        putIntoParent(typeRegionMap.get(RegionTypeEnum.PROVINCE), regionMap);
        putIntoParent(typeRegionMap.get(RegionTypeEnum.CITY), regionMap);
        putIntoParent(typeRegionMap.get(RegionTypeEnum.AREA), regionMap);
        putIntoParent(typeRegionMap.get(RegionTypeEnum.STREET), regionMap);
        return provinceList;
    }

    private void putIntoParent(List<RegionModel> regionModelList, Map<Long,RegionModel> regionMap) {
        if (CollectionUtils.isEmpty(regionModelList)){
            return;
        }
        for (RegionModel rm: regionModelList) {
            if (regionMap.get(rm.getId()) == null){
                regionMap.put(rm.getId(), rm);
            }
            if (rm.getParentId() != null){
                regionMap.get(rm.getParentId()).addChildRegion(rm);
            }
        }
    }

}
