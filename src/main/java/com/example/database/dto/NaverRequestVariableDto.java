package com.example.database.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NaverRequestVariableDto {
    private String query;
    private Integer display;
    private Integer start;
    private String sort;
}