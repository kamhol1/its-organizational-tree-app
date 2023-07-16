package com.example.demo.specifications;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification implements Specification<Employee> {

    private final List<SearchCriteria> searchCriteriaList = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        searchCriteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for(SearchCriteria criteria : searchCriteriaList) {
            if (criteria.getKey().equals("firstName") || criteria.getKey().equals("lastName")) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"
                        )
                );
            }
            if (criteria.getKey().equals("department")) {
                Join<Employee, Department> departmentJoin = root.join("department", JoinType.INNER);
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(departmentJoin.get("shortName")), "%" + criteria.getValue().toString().toLowerCase() + "%"
                        )
                );
            }
            if (criteria.getKey().equals("position")) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(criteria.getKey()).as(String.class)), "%" + criteria.getValue().toString().toLowerCase() + "%"
                        )
                );
            }
            if (criteria.getKey().equals("active")) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get(criteria.getKey()), criteria.getValue()
                        )
                );
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
