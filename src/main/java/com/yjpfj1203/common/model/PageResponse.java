package com.yjpfj1203.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PageResponse<T> {
    private Long totalCount;
    private int currentPage;
    private List<T> items;
}
