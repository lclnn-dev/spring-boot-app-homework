package com.example.demowithtests.service;

import com.example.demowithtests.domain.WorkPlace;

public interface WorkPlaceService {

    WorkPlace getById(Long id);

    WorkPlace create(WorkPlace workPlace);

    WorkPlace updateById(Long id, WorkPlace workPlace);

    void deleteById(Long id);
}
