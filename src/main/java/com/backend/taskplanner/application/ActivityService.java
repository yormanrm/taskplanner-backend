package com.backend.taskplanner.application;

import com.backend.taskplanner.domain.model.Activity;
import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.ports.IActivityRepository;

public class ActivityService {
    private final IActivityRepository iActivityRepository;

    public ActivityService(IActivityRepository iActivityRepository) {
        this.iActivityRepository = iActivityRepository;
    }

    public void deleteById(Integer id) {
        this.iActivityRepository.deleteById(id);
    }

}
