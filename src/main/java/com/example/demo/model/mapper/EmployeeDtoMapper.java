package com.example.demo.model.mapper;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.model.dto.EmployeeDto;
import com.example.demo.model.dto.EmployeeWriteDto;

public class EmployeeDtoMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .position(employee.getPosition().toString())
                .supervisor(employee.getSupervisor() != null ? employee.getSupervisor().getId() : null)
                .departmentId(employee.getDepartment().getId())
                .department(employee.getDepartment().getShortName())
                .division(employee.getDepartment().getDivision())
                .build();
    }

    public static EmployeeWriteDto mapToEmployeeWriteDto(Employee employee) {
        return EmployeeWriteDto.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .position(employee.getPosition())
                .department(employee.getDepartment().getId())
                .build();
    }

    public static Employee mapToEmployee(EmployeeWriteDto dto) {
        Department department = new Department();
        department.setId(dto.getDepartment());

        return Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .position(dto.getPosition())
                .department(department)
                .active(true)
                .build();
    }

    public static Employee mapToEmployee(EmployeeWriteDto employeeWriteDto, Employee toUpdate) {
        Department department = new Department();
        department.setId(employeeWriteDto.getDepartment());

        toUpdate.setFirstName(employeeWriteDto.getFirstName());
        toUpdate.setLastName(employeeWriteDto.getLastName());
        toUpdate.setPosition(employeeWriteDto.getPosition());
        toUpdate.setDepartment(department);

        return toUpdate;
    }
}
