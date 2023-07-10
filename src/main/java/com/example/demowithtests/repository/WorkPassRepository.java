package com.example.demowithtests.repository;

import com.example.demowithtests.domain.WorkPass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkPassRepository extends JpaRepository<WorkPass, Long> {
}