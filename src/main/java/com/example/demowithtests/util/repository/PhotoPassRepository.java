package com.example.demowithtests.util.repository;

import com.example.demowithtests.domain.PhotoPass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoPassRepository extends JpaRepository<PhotoPass, Long> {
}
