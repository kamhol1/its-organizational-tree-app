package com.example.demo.service;

import com.example.demo.model.dto.DepartmentDto;
import com.example.demo.model.mapper.DepartmentDtoMapper;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<DepartmentDto> findAll() {
        return repository.findAll().stream()
                .map(DepartmentDtoMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

}
