package com.backend.taskplanner.domain.ports;

import com.backend.taskplanner.domain.model.Activity;
import com.backend.taskplanner.domain.model.Status;

public interface IActivityRepository {
    Activity save(Activity activity);
    void updateStatusById(Integer id, Status status);
    void deleteById(Integer id);
}
