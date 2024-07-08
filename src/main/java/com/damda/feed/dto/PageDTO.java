package com.damda.feed.dto;

import lombok.Getter;

@Getter
public class PageDTO<T> {

    private T data;
    private boolean hasNextPage;

    public PageDTO(T data, boolean hasNextPage) {
        this.data = data;
        this.hasNextPage = hasNextPage;
    }
}
