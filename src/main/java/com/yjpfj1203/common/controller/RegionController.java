package com.yjpfj1203.common.controller;

import com.yjpfj1203.common.entity.Region;
import com.yjpfj1203.common.entity.repository.RegionRepository;
import com.yjpfj1203.common.enums.RegionTypeEnum;
import com.yjpfj1203.common.model.region.RegionBlockModel;
import com.yjpfj1203.common.model.region.RegionModel;
import com.yjpfj1203.common.model.region.RegionStringModel;
import com.yjpfj1203.common.model.region.RegionTreeModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Steven on 2016-07-14.
 * 区域选择服务
 */
@RestController
@RequestMapping(value = "/regions")
@Slf4j
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<RegionModel> queryRegions(@RequestParam(value = "type", required = false, defaultValue = "list") String type) {
        List<Region> provinces = regionRepository.queryRegionTree();
        switch (type) {
            case "tree":
                return provinces.stream().map(RegionModel::new).collect(Collectors.toList());
            case "list":
            default:
                List<Region> citys = provinces.stream().flatMap(province -> province.getRegions().stream()).collect(Collectors.toList());
                List<Region> areas = citys.stream().flatMap(city -> city.getRegions().stream()).collect(Collectors.toList());
                List<Region> allRegions = new ArrayList<>();
                allRegions.addAll(provinces);
                allRegions.addAll(citys);
                allRegions.addAll(areas);
                return allRegions.stream().map(r -> new RegionModel().RegionModelWithoutChildren(r)).collect(Collectors.toList());
        }
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.GET)
    public Collection<RegionTreeModel> queryRegions(@PathVariable(name = "ids") Collection<Long> ids,
                                                    @RequestParam("embed") Collection<String> embed) {
        Collection<Region> regions = regionRepository.findDistinctByIdIn(ids);
        Collection<RegionTreeModel> models = null;
        if (embed.contains("parent")) {
            models = regions.stream().map(region -> setRegionParent(null, region)).collect(Collectors.toList());
        } else {
            models = regions.stream().map(RegionTreeModel::new).collect(Collectors.toList());
        }
        return models == null ? new ArrayList<>() : models;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/parents/{id}")
    public Collection<RegionModel> queryRegionsByParentId(@PathVariable("id") Long parentId) {
        Collection<Region> regions = regionRepository.findDistinctByParentId(parentId);
        return regions.stream().map(RegionModel::new).collect(Collectors.toList());
    }


    @RequestMapping(method = RequestMethod.GET, value = "/parents/in/{ids}")
    public Collection<RegionModel> queryRegionsByParentIdIn(@PathVariable("ids") Long[] parentIds) {
        Collection<Region> regions = regionRepository.findDistinctByParentIdIn(Arrays.asList(parentIds));
        return regions.stream().map(RegionModel::new).collect(Collectors.toList());
    }

    /**
     * 返回指定areaId的带数据，list->list
     *
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{ids}/info")
    public List<RegionModel> queryRegionsByAreaIds(@PathVariable(name = "ids") Collection<Long> ids) {
        Collection<Region> regions = regionRepository.findDistinctByIdIn(ids);
        List<Long> cityIds = new ArrayList<>();
        regions.forEach(region -> cityIds.add(region.getParent().getId()));
        Collection<Region> cityRegions = regionRepository.findDistinctByIdIn(cityIds);
        List<RegionModel> cityRegionModels = new ArrayList<>();
        cityRegions.forEach(cityRegion -> {
            RegionModel cityRegionModel = new RegionModel();
            BeanUtils.copyProperties(cityRegion, cityRegionModel);
            regions.forEach(region -> {
                if (region.getParent().getId().equals(cityRegion.getId())) {
                    RegionModel areaModel = new RegionModel();
                    BeanUtils.copyProperties(region, areaModel);
                    if (cityRegionModel.getRegions() == null) {
                        cityRegionModel.setRegions(new ArrayList<>());
                    }
                    cityRegionModel.getRegions().add(areaModel);
                }
            });
            cityRegionModels.add(cityRegionModel);
        });
        return cityRegionModels;
    }

    private RegionTreeModel setRegionParent(RegionTreeModel regionTreeModel, Region region) {
        RegionTreeModel parentModel = new RegionTreeModel(region);
        parentModel.setRegion(regionTreeModel);

        if (region.getParent() != null) {
            return setRegionParent(parentModel, region.getParent());
        }
        return parentModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/info/blockId")
    public List<Long> queryRegionsByAreaIds(@RequestParam(value = "cityId", required = false) Long cityId, @RequestParam(value = "areaIds", required = false) List<Long> areaIds) {
        List<Long> blockIds;
        if (areaIds == null) {
            List<Long> areaIdsByCityId = regionRepository.findDistinctByParentId(cityId).stream().map(Region::getId).collect(Collectors.toList());
            blockIds = regionRepository.findByParentIdInAndType(areaIdsByCityId, RegionTypeEnum.BLOCK).stream().map(Region::getId).collect(Collectors.toList());
        } else {
            blockIds = regionRepository.findByParentIdInAndType(areaIds, RegionTypeEnum.BLOCK).stream().map(Region::getId).collect(Collectors.toList());
        }

        return blockIds;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/blocks")
    public List<RegionBlockModel> queryRegionBlockById(@RequestParam("ids") List<Long> ids) {
        Collection<RegionTreeModel> regionTreeModels = queryRegions(
                ids, Collections.singletonList("parent"));
        if (CollectionUtils.isEmpty(regionTreeModels))
            return new ArrayList<>(
            );
        return regionTreeModels.stream().map(RegionBlockModel::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/areas")
    public List<RegionBlockModel> queryRegionAreaByIds(@RequestParam("ids") List<Long> ids) {
        List<RegionBlockModel> regionBlockModels = new ArrayList<>();
        ids.forEach(
                id -> {
                    RegionBlockModel regionBlockModel = new RegionBlockModel();
                    Region areaRegion = regionRepository.queryById(id);
                    regionBlockModel.setAreaId(areaRegion.getId());
                    regionBlockModel.setAreaName(areaRegion.getName());

                    Region cityRegion = areaRegion.getParent();
                    regionBlockModel.setCityId(cityRegion.getId());
                    regionBlockModel.setCityName(cityRegion.getName());

                    Region provinceRegion = cityRegion.getParent();
                    regionBlockModel.setProvinceName(provinceRegion.getName());
                    regionBlockModel.setProvinceId(provinceRegion.getId());

                    regionBlockModels.add(regionBlockModel);
                }
        );
        return regionBlockModels;
    }


    @GetMapping(value = "/keyword")
    public List<RegionStringModel> queryRegionsByKeyword(@RequestParam("keyword") String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return new ArrayList<>();
        }
        List<Region> regions = regionRepository.queryByKeyword(keyword);
        Map<Long, String> regionMaps = regions.stream().map(this::findSubRegionAndParse2StringArray).reduce((m1, m2) -> {
            m1.putAll(m2);
            return m1;
        }).orElse(new HashMap<>());
        return regionMaps.entrySet().stream().map(m -> new RegionStringModel(m.getKey(), m.getValue())).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cities/{id}/areas/blocks")
    public List<RegionModel> queryAreasBlocksByCityId(@PathVariable(name = "id") Long cityId) {
        //获取所有区域的数据
        Collection<Region> areaRegions = regionRepository.findDistinctByParentId(cityId);
        //获取所有商圈的数据
        List<Long> areaIds = areaRegions.stream().map(Region::getId).collect(Collectors.toList());
        Collection<Region> blockRegions = regionRepository.findByParentIdIn(areaIds);

        List<RegionModel> models = areaRegions.stream().map(RegionModel::new).collect(Collectors.toList());
        models.forEach(model -> {
            Collection<Region> filterBlockRegions = blockRegions.stream().filter(region -> Objects.equals(region.getParent().getId(), model.getId())).collect(Collectors.toList());
            List<RegionModel> blockModels = filterBlockRegions.stream().map(RegionModel::new).collect(Collectors.toList());
            model.setRegions(blockModels);
        });

        return models;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cities/{ids}/areas")
    public List<RegionModel> queryAreasByCityId(@PathVariable(name = "ids") Long[] ids) {
        //获取所有城市的数据
        Collection<Region> cityRegions = regionRepository.findDistinctByIdIn(Arrays.asList(ids));
        //获取所有区域的数据
        List<Long> cityIds = cityRegions.stream().map(Region::getId).collect(Collectors.toList());
        Collection<Region> areaRegions = regionRepository.findByParentIdIn(cityIds);

        List<RegionModel> models = cityRegions.stream().map(RegionModel::new).collect(Collectors.toList());
        models.forEach(model -> {
            List<RegionModel> filterAreaRegions = areaRegions.stream()
                    .filter(region -> Objects.equals(region.getParent().getId(), model.getId()))
                    .map(RegionModel::new).collect(Collectors.toList());
            model.setRegions(filterAreaRegions);
        });

        return models;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter/cities/{ids}")
    public List<RegionModel> queryAllByCityIds(@PathVariable(name = "ids") Collection<Long> cityIds) {
        //获取所有城市的数据
        Collection<Region> cityRegions = regionRepository.findDistinctByIdIn(cityIds);
        //通过城市region中的parent_id获取省
        List<Long> provinceIds = cityRegions.stream().map(region -> region.getParent().getId()).collect(Collectors.toList());
        Collection<Region> provinceRegions = regionRepository.findDistinctByIdIn(provinceIds);
        //获取所有区域的数据
        Collection<Region> areaRegions = regionRepository.findByParentIdIn(cityIds);
        //获取所有商圈的数据
        List<Long> areaIds = areaRegions.stream().map(Region::getId).collect(Collectors.toList());
        Collection<Region> blockRegions = regionRepository.findByParentIdIn(areaIds);

        //组合
        List<RegionModel> provinceModels = provinceRegions.stream().map(RegionModel::new).collect(Collectors.toList());
        provinceModels.forEach(provinceModel -> {
            Collection<Region> filterCityRegion = cityRegions.stream().filter(region -> Objects.equals(region.getParent().getId(), provinceModel.getId())).collect(Collectors.toList());
            List<RegionModel> cityModels = filterCityRegion.stream().map(RegionModel::new).collect(Collectors.toList());
            provinceModel.setRegions(cityModels);

            cityModels.forEach(cityModel -> {
                Collection<Region> filterAreaRegions = areaRegions.stream().filter(region -> Objects.equals(region.getParent().getId(), cityModel.getId())).collect(Collectors.toList());
                List<RegionModel> areaModels = filterAreaRegions.stream().map(RegionModel::new).collect(Collectors.toList());
                cityModel.setRegions(areaModels);

                areaModels.forEach(areaModel -> {
                    Collection<Region> filterBlockRegions = blockRegions.stream().filter(region -> Objects.equals(region.getParent().getId(), areaModel.getId())).collect(Collectors.toList());
                    List<RegionModel> blockModels = filterBlockRegions.stream().map(RegionModel::new).collect(Collectors.toList());
                    areaModel.setRegions(blockModels);
                });
            });
        });

        return provinceModels;
    }

    /**
     * 给定一个Region,找到每一个最底层的子节点,并调用parseRegionTree2SimpleString()将每一个子节点Region的区域层次组合成字符串
     * 存入一个map中,map的key是最底层Region的Id,保证不重复出现同一个区域
     * 例如滨江区Region,会把所有的商圈取出并组合成字符串数列,"浙江省/杭州市/滨江区/滨江区政府","浙江省/杭州市/滨江区/彩虹城商圈",...
     *
     * @param region
     * @return 接收结果
     */
    private Map<Long, String> findSubRegionAndParse2StringArray(Region region) {
        if (CollectionUtils.isNotEmpty(region.getRegions())) {
            return region.getRegions().stream().map(this::findSubRegionAndParse2StringArray).reduce((m1, m2) -> {
                m1.putAll(m2);
                return m1;
            }).orElse(new HashMap<>());
        } else {
            Map<Long, String> regionMaps = new HashMap<>();
            regionMaps.putIfAbsent(region.getId(), parseRegionTree2SimpleString(region));
            return regionMaps;
        }
    }

    /**
     * 给定一个Region,将它的父节点依次拼接到前面
     * 例如滨江区Region最终会返回 "浙江省/杭州市/滨江区"
     * 例如滨江区政府Region最终归会返回 "浙江省/杭州市/滨江区/滨江区政府"
     *
     * @param region 区域Region
     * @return 字符串
     */
    private String parseRegionTree2SimpleString(Region region) {
        if (region.getParent() != null) {
            return parseRegionTree2SimpleString(region.getParent()) + "/" + region.getName();
        } else {
            return region.getName();
        }
    }
}
