package com.example.demowithtests.repository;

import com.example.demowithtests.domain.WorkPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkPlaceRepository extends JpaRepository<WorkPlace, Long> {
}
