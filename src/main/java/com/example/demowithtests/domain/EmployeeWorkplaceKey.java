package com.example.demowithtests.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Accessors(chain = true)
public class EmployeeWorkplaceKey implements Serializable {

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "workplace_id")
    private Long workPlaceId;

}
