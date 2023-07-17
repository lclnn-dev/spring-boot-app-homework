package com.example.demowithtests.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "workplaces")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    private String name;

    private Boolean airCondition = Boolean.TRUE;

    private Boolean coffeeMachine = Boolean.TRUE;

    @OneToMany(mappedBy = "workPlace")
    Set<EmployeeWorkplace> employees;
}
