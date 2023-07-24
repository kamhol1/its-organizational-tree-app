package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class DepartmentDto {
    private int id;
    private String shortName;
}
