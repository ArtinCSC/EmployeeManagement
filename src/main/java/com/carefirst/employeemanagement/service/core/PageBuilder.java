package com.carefirst.employeemanagement.service.core;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class PageBuilder {

    public static final int DEFAULT_PAGE_SIZE = 50;

    private final List<Sort.Order> order = new ArrayList<>();
    private int page = 0;
    private int size = DEFAULT_PAGE_SIZE;

    public Pageable build() {
        return PageRequest.of(this.page, this.size, Sort.by(this.order));
    }

    public PageBuilder size(Integer size) {
        if (size != null) {
            this.size = size;
        }
        return this;
    }

    public PageBuilder page(Integer page) {
        if (page != null) {
            this.page = page;
        }

        return this;
    }

    public PageBuilder sort(List<String> sort) {
        if (sort != null) {
            for (String s : sort) {
                String[] parts = StringUtils.split(s, ":");
                String d = (parts != null && parts.length > 1) ? parts[1] : "ASC";
                Sort.Direction direction = d != null ? Sort.Direction.valueOf(d) : null;
                if (parts != null) {
                    order.add(new Sort.Order(direction, parts[0]));
                }
            }
        }
        return this;
    }
}
