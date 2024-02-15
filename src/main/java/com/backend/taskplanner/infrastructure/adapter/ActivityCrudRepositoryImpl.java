package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.domain.model.Activity;
import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.ports.IActivityRepository;
import com.backend.taskplanner.infrastructure.mapper.ActivityMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ActivityCrudRepositoryImpl implements IActivityRepository {
    private final IActivityCrudRepository iActivityCrudRepository;
    private final ActivityMapper activityMapper;

    public ActivityCrudRepositoryImpl(IActivityCrudRepository iActivityCrudRepository, ActivityMapper activityMapper) {
        this.iActivityCrudRepository = iActivityCrudRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public void deleteById(Integer id) {
        iActivityCrudRepository.findById(id).orElseThrow(() -> new RuntimeException("Activity not found"));
        iActivityCrudRepository.deleteById(id);
    }
}
