package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.domain.model.Activity;
import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.model.Task;
import com.backend.taskplanner.domain.ports.IActivityRepository;
import com.backend.taskplanner.domain.ports.ITaskRepository;
import com.backend.taskplanner.infrastructure.entity.ActivityEntity;
import com.backend.taskplanner.infrastructure.entity.TaskEntity;
import com.backend.taskplanner.infrastructure.entity.UserEntity;
import com.backend.taskplanner.infrastructure.mapper.TaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TaskCrudRepositoryImpl implements ITaskRepository {
    private final ITaskCrudRepository iTaskCrudRepository;
    private final IActivityRepository iActivityRepository;
    private final TaskMapper taskMapper;

    public TaskCrudRepositoryImpl(ITaskCrudRepository iTaskCrudRepository, IActivityRepository iActivityRepository, TaskMapper taskMapper) {
        this.iTaskCrudRepository = iTaskCrudRepository;
        this.iActivityRepository = iActivityRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional
    public Task save(Task task) {
        if (task.getId() != null) {
            Task taskFinded = this.findById(task.getId());

            List<Activity> existingActivities = taskFinded.getActivities();
            List<Activity> newActivities = task.getActivities();

            for (Activity existingActivity : existingActivities) {
                boolean toEliminate = true;
                for (Activity newActivity : newActivities) {
                    if (existingActivity.getId() == null || existingActivity.getId().equals(newActivity.getId())) {
                        toEliminate = false;
                        break;
                    }
                }
                if (toEliminate) {
                    iActivityRepository.deleteById(existingActivity.getId());
                }
            }
        }
        TaskEntity taskEntity = taskMapper.toTaskEntity(task);
        taskEntity.getActivities().forEach(activityEntity -> activityEntity.setTaskEntity(taskEntity));
        TaskEntity taskSavedEntity = iTaskCrudRepository.save((taskEntity));
        Task taskSaved = taskMapper.toTask(taskSavedEntity);
        return taskSaved;
    }

    @Override
    public Task findById(Integer id) {
        Optional<TaskEntity> taskEntityOptional = iTaskCrudRepository.findById(id);
        return taskEntityOptional.map(taskMapper::toTask).orElseGet(Task::new);
    }

    @Override
    public Iterable<Task> findByUserId(Integer userId, Boolean archived) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return taskMapper.toTasks(iTaskCrudRepository.findByUserEntity(userEntity, archived));
    }

    @Override
    public Iterable<Task> findByStatus(Integer userId, Status status, Boolean archived) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return taskMapper.toTasks(iTaskCrudRepository.findByUserEntityAndStatus(userEntity, status, archived));
    }

    @Override
    public Iterable<Task> findByNameOrDescription(Integer userId, String text, Boolean archived) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return taskMapper.toTasks(iTaskCrudRepository.findByNameOrDescription(userEntity, text, archived));
    }

    @Override
    public Iterable<Task> findByDatesRange(Integer userId, String startDate, String endDate, Boolean archived) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59");
        Iterable<TaskEntity> taskEntities = iTaskCrudRepository.findByDatesRange(userEntity, startDateTime, endDateTime, archived);
        return taskMapper.toTasks(taskEntities);
    }

    @Override
    public void updateStatusById(Integer id, Status status) {
        iTaskCrudRepository.updateStatusById(id, status);
    }

    @Override
    public void updateArchivedById(Integer id, Boolean archived) {
        iTaskCrudRepository.updateArchivedById(id, archived);
    }

    @Override
    public void deleteById(Integer id) {
        iTaskCrudRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        iTaskCrudRepository.deleteById(id);
    }
}
