package com.backend.taskplanner.domain.ports;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.model.Task;

public interface ITaskRepository {
    Task save(Task task);

    Task findById(Integer id);

    Iterable<Task> findByUserId(Integer userId);

    Iterable<Task> findByStatus(Integer userId, Status status);

    Iterable<Task> findArchived(Integer userId);

    Iterable<Task> findByNameOrDescription(Integer userId, String text);

    Iterable<Task> findByDatesRange(Integer userId, String startDate, String endDate);

    void updateStatusById(Integer id, Status status);

    void updateArchivedById(Integer id, Boolean archived);

    void deleteById(Integer id);
}
