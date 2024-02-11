package com.backend.taskplanner.domain.ports;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.model.Task;

public interface ITaskRepository {
    Task save(Task task);

    Iterable<Task> findAll();

    Task findById(Integer id);

    Iterable<Task> findByUserId(Integer userId);

    Iterable<Task> findByStatus(Status status);

    Iterable<Task> findByNameOrDescription(String text);

    void updateStatusById(Integer id, Status status);

    void deleteById(Integer id);
}
