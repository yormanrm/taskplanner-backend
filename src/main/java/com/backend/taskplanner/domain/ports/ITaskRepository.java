package com.backend.taskplanner.domain.ports;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.model.Task;

public interface ITaskRepository {
    Task save(Task task);

    Task findById(Integer id);

    Iterable<Task> findByUserId(Integer userId, Boolean archived);

    Iterable<Task> findByStatus(Integer userId, Status status, Boolean archived);

    Iterable<Task> findByNameOrDescription(Integer userId, String text, Boolean archived);

    Iterable<Task> findByDatesRange(Integer userId, String startDate, String endDate, Boolean archived);

    void updateStatusById(Integer id, Status status);

    void updateArchivedById(Integer id, Boolean archived);

    void deleteById(Integer id);
}
