package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class EmployeeDto {
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private Integer supervisor;
    private int departmentId;
    private String department;
    private String division;
}
