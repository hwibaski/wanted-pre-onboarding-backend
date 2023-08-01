package com.mypost.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearch {
    private static final int MAX_SIZE = 2000;

    private Integer page = 1;

    private Integer size = 10;

    public long getOffset() {
        return (long) (Math.max(1, page) - 1) * Math.min(size, MAX_SIZE);
    }

    public PostSearch(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PostSearch() {
        this.page = 1;
        this.size = 10;
    }
}
