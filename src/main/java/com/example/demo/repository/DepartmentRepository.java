package com.example.demo.repository;

import com.example.demo.model.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @EntityGraph(
            type = EntityGraphType.FETCH,
            attributePaths = {
                    "manager",
                    "parentDepartment"
            }
    )
    List<Department> findAll();
}
