package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    @EntityGraph(
            type = EntityGraphType.FETCH,
            attributePaths = {
                    "department",
                    "department.parentDepartment",
                    "department.manager"
            }
    )
    Page<Employee> findAll(Specification spec, Pageable pageable);

    @EntityGraph(
            type = EntityGraphType.FETCH,
            attributePaths = {
                    "department",
                    "department.parentDepartment",
                    "department.parentDepartment.manager",
                    "department.manager",
                    "department.manager.department"
            }
    )
    Optional<Employee> findById(int id);
}
