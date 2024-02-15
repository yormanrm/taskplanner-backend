package com.backend.taskplanner.domain.ports;

import com.backend.taskplanner.domain.model.Activity;
import com.backend.taskplanner.domain.model.Status;

public interface IActivityRepository {
    void deleteById(Integer id);
}
