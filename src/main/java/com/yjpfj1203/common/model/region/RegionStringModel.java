package com.yjpfj1203.common.model.region;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Steven on 2017/6/21.
 */
@Getter
@Setter
@NoArgsConstructor
public class RegionStringModel {

    private Long id;
    private String name;

    public RegionStringModel(Long id, String regionTreeName) {
        this.id = id;
        int index = regionTreeName.indexOf("/");
        if (index != -1) {
//            this.name = regionTreeName;
            this.name = regionTreeName.substring(index + 1, regionTreeName.length());
        } else {
            this.name = regionTreeName;
        }
    }
}
