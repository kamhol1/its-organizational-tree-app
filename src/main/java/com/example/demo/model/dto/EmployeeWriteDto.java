package com.example.demo.model.dto;

import com.example.demo.model.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmployeeWriteDto {
    private String firstName;
    private String lastName;
    private Position position;
    private int department;
}
