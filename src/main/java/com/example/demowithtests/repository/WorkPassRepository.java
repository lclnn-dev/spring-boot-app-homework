package com.example.demowithtests.repository;

import com.example.demowithtests.domain.WorkPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface WorkPassRepository extends JpaRepository<WorkPass, Long>, RevisionRepository<WorkPass, Long, Long> {

    List<WorkPass> findAllByPrevEmployeeId(Integer employeeId);
}