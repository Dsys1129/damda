package com.damda.feed.dto;

import lombok.Setter;

@Setter
public class PageParam {
    private String filter = "11";
    private String sort = "hot";
    private Integer page = 1;
    private Integer size = 20;

    public Integer getPage() {
        if (page == 0) {
            return page;
        }
        return this.page - 1;
    }

    public String getSort() {
        if ("new".equals(this.sort)) {
            return "new";
        }
        return "hot";
    }

    public String getFilter() {
        return filter;
    }

    public Integer getSize() {
        return size;
    }
}
