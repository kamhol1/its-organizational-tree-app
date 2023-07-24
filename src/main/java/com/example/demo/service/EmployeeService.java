package com.example.demo.service;

import com.example.demo.exception.CannotChangeDepartmentException;
import com.example.demo.exception.CannotDeactivateManagerException;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.dto.EmployeeDto;
import com.example.demo.model.dto.EmployeeWriteDto;
import com.example.demo.model.mapper.EmployeeDtoMapper;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.specifications.EmployeeSpecification;
import com.example.demo.specifications.SearchCriteria;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.model.mapper.EmployeeDtoMapper.*;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository repository;

    EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Page<EmployeeDto> getActiveEmployees(String firstNameFilter, String lastNameFilter, String positionFilter, String departmentFilter, Pageable pageable, String sortBy, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        EmployeeSpecification spec = new EmployeeSpecification();
        spec.add(new SearchCriteria("active", true));

        if (firstNameFilter != null) {
            spec.add(new SearchCriteria("firstName", firstNameFilter));
        }
        if (lastNameFilter != null) {
            spec.add(new SearchCriteria("lastName", lastNameFilter));
        }
        if (positionFilter != null) {
            spec.add(new SearchCriteria("position", positionFilter));
        }
        if (departmentFilter != null) {
            spec.add(new SearchCriteria("department", departmentFilter));
        }

        return repository.findAll(spec, page).map(EmployeeDtoMapper::mapToEmployeeDto);
    }

    public EmployeeDto getEmployee(int id) {
        return mapToEmployeeDto(repository.findById(id).orElseThrow(() -> {
            logger.info("Employee with id " + id + " not found");
            throw new EmployeeNotFoundException();
        }));
    }

    public List<EmployeeDto> getEmployeeManagersById(int id) {
        Employee employee = repository.findById(id).orElseThrow(() -> {
            logger.info("Employee with id " + id + " not found");
            throw new EmployeeNotFoundException();
        });

        List<Employee> managers = new ArrayList<>();

        while (employee != null && employee.getSupervisor() != null) {
            employee = employee.getSupervisor();
            managers.add(employee);
        }

        return managers.stream()
                .map(EmployeeDtoMapper::mapToEmployeeDto)
                .toList();
    }

    @Transactional
    public EmployeeDto createEmployee(EmployeeWriteDto dto) {
        Employee employee = mapToEmployee(dto);
        Employee created = repository.save(employee);
        logger.info("New employee created with id: " + created.getId());

        return mapToEmployeeDto(created);
    }

    @Transactional
    public EmployeeDto updateEmployee(int id, EmployeeWriteDto dto) {
        Employee employee = repository.findById(id).orElseThrow(() -> {
            logger.info("Employee with id " + id + " not found");
            throw new EmployeeNotFoundException();
        });

        // Check if employee is a department manager, if so, throw an exception
        if (employee.isManager() && employee.getDepartment().getId() != dto.getDepartment()) {
            logger.info("Failed to update an employee with id " + employee.getId() + " - tried to change department of the employee who is the manager of this department");
            throw new CannotChangeDepartmentException();
        }

        Employee updated = repository.save(mapToEmployee(dto, employee));

        logger.info("Employee with id " + updated.getId() + " has been updated");

        return mapToEmployeeDto(updated);
    }

    @Transactional
    public void deactivateEmployee(int id) {
        Employee employee = repository.findById(id).orElseThrow(() -> {
            logger.info("Employee with id " + id + " not found");
            throw new EmployeeNotFoundException();
        });

        if (employee.isManager()) {
            logger.info("Failed to deactivate an employee with id " + employee.getId() + " because the employee is a department manager");
            throw new CannotDeactivateManagerException();
        }

        employee.setActive(false);
        repository.save(employee);

        logger.info("Employee with id " + id + " has been deactivated");
    }
}
