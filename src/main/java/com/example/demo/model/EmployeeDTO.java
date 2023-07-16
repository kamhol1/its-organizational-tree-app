package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private Position position;
    private boolean active;
    private Department department;
    private Employee supervisor;
}
