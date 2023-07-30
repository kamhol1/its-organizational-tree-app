package com.example.demo.controller;

import com.example.demo.model.dto.EmployeeDto;
import com.example.demo.model.dto.EmployeeWriteDto;
import com.example.demo.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

    private final EmployeeService service;

    EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/active")
    ResponseEntity<Page<EmployeeDto>> getActiveEmployees(Pageable pageable,
                                                         @RequestParam(value = "firstName", required = false) String firstNameFilter,
                                                         @RequestParam(value = "lastName", required = false) String lastNameFilter,
                                                         @RequestParam(value = "position", required = false) String positionFilter,
                                                         @RequestParam(value = "department", required = false) String departmentFilter,
                                                         @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "asc") String sortOrder) {

        return ResponseEntity.ok(service.getActiveEmployees(firstNameFilter, lastNameFilter, positionFilter, departmentFilter, pageable, sortBy, sortOrder));
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeDto> getEmployee(@PathVariable int id) {
        return ResponseEntity.ok(service.getEmployee(id));
    }

    @GetMapping("/{id}/managers")
    ResponseEntity<List<EmployeeDto>> getEmployeeManagersById(@PathVariable int id) {
        return ResponseEntity.ok(service.getEmployeeManagersById(id));
    }

    @PostMapping
    ResponseEntity<MessageResponse> createEmployee(@RequestBody EmployeeWriteDto dto) {
        EmployeeDto created = service.createEmployee(dto);
        return ResponseEntity.ok(new MessageResponse("Nowy pracownik został dodany. " + "Id pracownika: " + created.getId()));
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateEmployee(@PathVariable int id, @RequestBody EmployeeWriteDto employeeUpdateDto) {
        service.updateEmployee(id, employeeUpdateDto);
        return ResponseEntity.ok(new MessageResponse("Dane pracownika zostały zaktualizowane"));
    }

    @PatchMapping("/{id}/deactivate")
    ResponseEntity<MessageResponse> deactivateEmployee(@PathVariable int id) {
        EmployeeDto deactivated = service.deactivateEmployee(id);
        return ResponseEntity.ok(new MessageResponse("Pracownik o Id " + deactivated.getId() + " został usunięty"));
    }

}
