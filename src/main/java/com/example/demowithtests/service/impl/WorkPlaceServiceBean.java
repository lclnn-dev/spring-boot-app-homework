package com.example.demowithtests.service.impl;

import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.util.repository.WorkPlaceRepository;
import com.example.demowithtests.service.WorkPlaceService;
import org.springframework.stereotype.Service;

@Service
public class WorkPlaceServiceBean implements WorkPlaceService {

    private final WorkPlaceRepository workPlaceRepository;

    public WorkPlaceServiceBean(WorkPlaceRepository workPlaceRepository) {
        this.workPlaceRepository = workPlaceRepository;
    }

    @Override
    public WorkPlace getById(Long id) {
        return workPlaceRepository.findById(id).orElseThrow();
    }

    @Override
    public WorkPlace create(WorkPlace workPlace) {
        return workPlaceRepository.save(workPlace);
    }
}
