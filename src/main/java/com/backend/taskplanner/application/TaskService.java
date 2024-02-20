package com.backend.taskplanner.application;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.model.Task;
import com.backend.taskplanner.domain.ports.ITaskRepository;

public class TaskService {
    private final ITaskRepository iTaskRepository;

    public TaskService(ITaskRepository iTaskRepository) {
        this.iTaskRepository = iTaskRepository;
    }

    public Task save(Task task) {
        return this.iTaskRepository.save(task);
    }

    public Task findById(Integer id) {
        return this.iTaskRepository.findById(id);
    }

    public Iterable<Task> findByUserId(Integer userId) {
        return this.iTaskRepository.findByUserId(userId);
    }

    public Iterable<Task> findByStatus(Integer userId, Status status) {
        return this.iTaskRepository.findByStatus(userId, status);
    }

    public Iterable<Task> findByNameOrDescription(Integer userId, String text) {
        return this.iTaskRepository.findByNameOrDescription(userId, text);
    }

    public Iterable<Task> findByDatesRange(Integer userId, String startDate, String endDate){
        return this.iTaskRepository.findByDatesRange(userId, startDate, endDate);
    }

    public void updateStatusById(Integer id, Status status) {
        this.iTaskRepository.updateStatusById(id, status);
    }

    public void deleteById(Integer id) {
        this.iTaskRepository.deleteById(id);
    }

}
