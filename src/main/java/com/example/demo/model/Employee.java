package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Position position;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

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

    @JsonIgnore
    public Employee getManager() {
        Department department = this.getDepartment();
        if (department != null) {
            Employee manager = department.getManager();
            if (manager != null) {
                if (this.getId() == manager.getId()) {
                    Department parentDepartment = department.getParentDepartment();
                    return parentDepartment != null ? parentDepartment.getManager() : null;
                } else {
                    return manager;
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public Employee getSupervisor() {
        if (this.isManager()) {
            return this.getDepartment().getParentDepartment() != null ? this.getDepartment().getParentDepartment().getManager() : null;
        } else {
            return this.getDepartment().getManager();
        }
    }

    @JsonIgnore
    public boolean isManager() {
        return this.equals(this.getDepartment().getManager());
    }

}
