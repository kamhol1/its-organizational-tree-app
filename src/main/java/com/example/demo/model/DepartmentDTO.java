package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentDTO {
    private int id;
    private String name;
    private String shortName;
    private String division;
    private Employee manager;
    private Department parentDepartment;
}
