package com.example.demowithtests.service.impl;

import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.repository.WorkPlaceRepository;
import com.example.demowithtests.service.WorkPlaceService;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkPlaceServiceBean implements WorkPlaceService {

    private final WorkPlaceRepository workPlaceRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public WorkPlace getById(Long id) {
        return workPlaceRepository.findById(id).orElseThrow();
    }

    @Override
    public WorkPlace create(WorkPlace workPlace) {
        return workPlaceRepository.save(workPlace);
    }

    @Override
    public WorkPlace updateById(Long workPlaceId, WorkPlace workPlace) {
        WorkPlace existingWorkPlace = workPlaceRepository.findById(workPlaceId)
                .orElseThrow(() -> new ResourceNotFoundException(WorkPass.class, "id", workPlaceId.toString()));

        boolean isUpdated = false;

        if (workPlace.getName() != null && !workPlace.getName().equals(existingWorkPlace.getName())) {
            existingWorkPlace.setName(workPlace.getName());
            isUpdated = true;
        }

        if (workPlace.getAirCondition() != null && !workPlace.getAirCondition().equals(existingWorkPlace.getAirCondition())) {
            existingWorkPlace.setAirCondition(workPlace.getAirCondition());
            isUpdated = true;
        }

        if (workPlace.getCoffeeMachine() != null && !workPlace.getCoffeeMachine().equals(existingWorkPlace.getCoffeeMachine())) {
            existingWorkPlace.setCoffeeMachine(workPlace.getCoffeeMachine());
            isUpdated = true;
        }

        if (isUpdated) {
            return workPlaceRepository.save(existingWorkPlace);
        } else {
            return existingWorkPlace;
        }
    }

    @Override
    public void deleteById(Long id) {
        workPlaceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIdEM(Long id) {
        WorkPlace workPlace = entityManager.find(WorkPlace.class, id);
        entityManager.remove(workPlace);
    }
}
