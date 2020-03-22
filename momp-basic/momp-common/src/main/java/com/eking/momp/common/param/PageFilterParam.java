package com.eking.momp.common.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageFilterParam<T> {
    private int pageIndex;
    private int pageSize;
    private String sortBy;
    private Boolean asc;
    private T filter;
}
