package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "division", nullable = false)
    private String division;

    @JsonIgnore
    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    @JsonIgnore
    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "parent_department_id")
    private Department parentDepartment;

    @Column(name = "created_on", columnDefinition = "timestamp", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_on", columnDefinition = "timestamp", nullable = false)
    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    void preMerge() {
        updatedOn = LocalDateTime.now();
    }

}
