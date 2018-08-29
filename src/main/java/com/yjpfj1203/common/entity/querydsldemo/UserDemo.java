package com.yjpfj1203.common.entity.querydsldemo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yjpfj1203.common.entity.querydsldemo.enums.CurrencyEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 无确切含义的类，仅供技术参考
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@NamedEntityGraphs({
        //加载Trade时，同时加载出所有item，promotion及item下面的promotion
        @NamedEntityGraph(name = "UserDemoNEG",
                attributeNodes = {
                        @NamedAttributeNode(value = "currencySet"),
                        @NamedAttributeNode(value = "regionIds"),
                        @NamedAttributeNode(value = "tradeSet")
                })
})
public class UserDemo {
    public static final String USER_DEMO_NEG = "UserDemoNEG";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(value = EnumType.STRING)
    private Set<CurrencyEnum> currencySet;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<Long> regionIds;

    @JsonIgnore
    @JsonIgnoreProperties(value = {"trade", "hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> tradeSet;

    private String name;
}
