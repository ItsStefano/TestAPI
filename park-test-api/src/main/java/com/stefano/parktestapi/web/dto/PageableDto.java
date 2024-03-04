package com.stefano.parktestapi.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PageableDto {

    private List content = new ArrayList<>();
    private boolean first;
    private boolean last;
    @JsonProperty("pageNumber")
    private int number;
    @JsonProperty("pageElements")
    private int numberOfElements;
    private int size;
    private int totalElements;
    private int totalPages;
}
