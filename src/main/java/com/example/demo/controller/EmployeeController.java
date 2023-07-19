package com.example.demo.controller;

import com.example.demo.model.EmployeeDTO;
import com.example.demo.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    private final EmployeeService service;

    EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/active")
    ResponseEntity<Page<EmployeeDTO>> getActiveEmployees(Pageable pageable,
                                                         @RequestParam(value = "firstName", required = false) String firstNameFilter,
                                                         @RequestParam(value = "lastName", required = false) String lastNameFilter,
                                                         @RequestParam(value = "position", required = false) String positionFilter,
                                                         @RequestParam(value = "department", required = false) String departmentFilter,
                                                         @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String sortOrder) {

        return ResponseEntity.ok(service.getActiveEmployees(firstNameFilter, lastNameFilter, positionFilter, departmentFilter, pageable, sortBy, sortOrder));
    }

    @GetMapping("/{id}/details")
    ResponseEntity<?> getEmployeeDetailsById(@PathVariable int id) {
        return ResponseEntity.ok(service.getEmployeeDetails(id));
    }

    @GetMapping("/{id}/managers")
    ResponseEntity<?> getEmployeeManagersById(@PathVariable int id) {
        return ResponseEntity.ok(service.getEmployeeManagersById(id));
    }

    @PostMapping
    ResponseEntity<MessageResponse> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        service.createEmployee(employeeDTO);
        return ResponseEntity.ok().body(new MessageResponse("Pracownik został dodany."));
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateEmployee(@PathVariable int id, @RequestBody EmployeeDTO employeeDTO) {
        service.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(new MessageResponse("Dane pracownika zostały zaktualizowane."));
    }

    @PatchMapping("/{id}/deactivate")
    ResponseEntity<MessageResponse> deactivateEmployee(@PathVariable int id) {
        service.deactivateEmployee(id);
        return ResponseEntity.ok(new MessageResponse("Pracownik został usunięty."));
    }

}
