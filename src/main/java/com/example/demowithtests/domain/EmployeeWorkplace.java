package com.example.demowithtests.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_workplaces")
@Getter
@Setter
@ToString
public class EmployeeWorkplace {

    @EmbeddedId
    EmployeeWorkplaceKey id = new EmployeeWorkplaceKey();

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    Employee employee;

    @ManyToOne
    @MapsId("workPlaceId")
    @JoinColumn(name = "workplace_id")
    WorkPlace workPlace;

    @Column(name = "is_active")
    Boolean isActive = Boolean.TRUE;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "end_date")
    LocalDateTime endDate;
}
