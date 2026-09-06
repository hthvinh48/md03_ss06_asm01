package com.example.asm01.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> items;
    private Integer page;
    private Integer size;
    private Long totalItems;
    private Integer totalPages;
    private Boolean isLast;
}