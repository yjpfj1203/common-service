package com.yjpfj1203.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjpfj1203.common.enums.RegionTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author yjpfj1203
 * 省、市、区、街道
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Region implements Serializable {

    public static final long serialVersionUID = 31312312313L;

    @Id
    //strategy = GenerationType.IDENTITY 这个只适用于mysql，如果是oracle，可查下文档
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //邮政编码
    private String code;

    //区域名称
    private String name;

    //类型: 省、市、区、街道
    @Enumerated(EnumType.STRING)
    private RegionTypeEnum type;

    private Long parentId;

    @Digits(integer = 3, fraction = 12)
    private BigDecimal longitude = BigDecimal.valueOf(0);

    @Digits(integer = 3, fraction = 12)
    private BigDecimal latitude = BigDecimal.valueOf(0);

    @JsonIgnore
    private Boolean active = true;

    @CreatedDate
    @JsonIgnore
    private Date dateCreated;

    @LastModifiedDate
    @JsonIgnore
    private Date dateUpdate;



    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", dateCreated=" + dateCreated +
                ", dateUpdate=" + dateUpdate +
                ", type=" + type +
                '}';
    }

    public BigDecimal getLongitude() {
        if (this.longitude == null) {
            return BigDecimal.ZERO;
        } else {
            return this.longitude;
        }
    }

    public BigDecimal getLatitude() {
        if (this.latitude == null) {
            return BigDecimal.ZERO;
        } else {
            return this.latitude;
        }
    }


}
