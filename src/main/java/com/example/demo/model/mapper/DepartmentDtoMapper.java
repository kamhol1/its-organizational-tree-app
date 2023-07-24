package com.example.demo.model.mapper;

import com.example.demo.model.Department;
import com.example.demo.model.dto.DepartmentDto;

public class DepartmentDtoMapper {

    public static DepartmentDto mapToDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .shortName(department.getShortName())
                .build();
    }
}
