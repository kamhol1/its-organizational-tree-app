package com.example.demo.service;

import com.example.demo.exception.CannotChangeDepartmentException;
import com.example.demo.exception.CannotDeactivateManagerException;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeDTO;
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
import java.util.function.Function;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository repository;

    EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Page<EmployeeDTO> getActiveEmployees(String firstNameFilter, String lastNameFilter, String positionFilter, String departmentFilter, Pageable pageable, String sortBy, String sortOrder) {
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

        return repository.findAll(spec, page).map(mapToEmployeeDTO);
    }

    public EmployeeDTO getEmployeeDetails(int id) {
        Employee employee = repository.findById(id).orElseThrow(() -> {
            logger.info("Employee with id " + id + " not found");
            throw new EmployeeNotFoundException();
        });

        return mapToEmployeeDTO.apply(employee);
    }

    public List<EmployeeDTO> getEmployeeManagersById(int id) {
        Employee employee = repository.findById(id).orElseThrow(() -> {
            logger.info("Employee with id " + id + " not found");
            throw new EmployeeNotFoundException();
        });

        List<Employee> managers = new ArrayList<>();

        while (employee != null && employee.getManager() != null) {
            employee = employee.getManager();
            managers.add(employee);
        }

        return managers.stream()
                .map(mapToEmployeeDTO)
                .toList();
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapToEmployee.apply(employeeDTO);
        Employee created = repository.save(employee);
        logger.info("New employee created with id: " + created.getId());

        return mapToEmployeeDTO.apply(created);
    }

    @Transactional
    public EmployeeDTO updateEmployee(int id, EmployeeDTO employeeDTO) {
        Employee employee = repository.findById(id).orElseThrow(() -> {
            logger.info("Employee with id " + id + " not found");
            throw new EmployeeNotFoundException();
        });

        // Check if employee is a department manager, if so, throw an exception
        if (employee.isManager() && employee.getDepartment().getId() != employeeDTO.getDepartment().getId()) {
            logger.info("Failed to update an employee with id " + employee.getId() + " - tried to change department of the employee who is the manager of this department");
            throw new CannotChangeDepartmentException();
        }

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setPosition(employeeDTO.getPosition());
        employee.setActive(employeeDTO.isActive());
        employee.setDepartment(employeeDTO.getDepartment());

        Employee updated = repository.save(employee);
        logger.info("Employee with id " + updated.getId() + " has been updated");

        return mapToEmployeeDTO.apply(updated);
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

    private final Function<EmployeeDTO, Employee> mapToEmployee = e -> Employee.builder()
            .id(e.getId())
            .firstName(e.getFirstName())
            .lastName(e.getLastName())
            .position(e.getPosition())
            .active(e.isActive())
            .department(e.getDepartment())
            .build();


    private final Function<Employee, EmployeeDTO> mapToEmployeeDTO = e -> EmployeeDTO.builder()
            .id(e.getId())
            .firstName(e.getFirstName())
            .lastName(e.getLastName())
            .position(e.getPosition())
            .active(e.isActive())
            .department(e.getDepartment())
            .supervisor(e.getSupervisor())
            .build();
}
