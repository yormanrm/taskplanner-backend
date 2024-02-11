package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.model.Task;
import com.backend.taskplanner.domain.ports.ITaskRepository;
import com.backend.taskplanner.infrastructure.entity.ActivityEntity;
import com.backend.taskplanner.infrastructure.entity.TaskEntity;
import com.backend.taskplanner.infrastructure.entity.UserEntity;
import com.backend.taskplanner.infrastructure.mapper.TaskMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskCrudRepositoryImpl implements ITaskRepository {
    private final ITaskCrudRepository iTaskCrudRepository;
    private final TaskMapper taskMapper;

    public TaskCrudRepositoryImpl(ITaskCrudRepository iTaskCrudRepository, TaskMapper taskMapper) {
        this.iTaskCrudRepository = iTaskCrudRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Task save(Task task) {
        TaskEntity taskEntity = taskMapper.toTaskEntity(task);
        List<ActivityEntity> activityEntities = taskEntity.getActivities();
        if (!activityEntities.isEmpty()) {
            taskEntity.getActivities().forEach(activityEntity -> activityEntity.setTaskEntity(taskEntity));
        }
        return taskMapper.toTask(iTaskCrudRepository.save(taskEntity));
    }

    @Override
    public Task findById(Integer id) {
        Optional<TaskEntity> taskEntityOptional = iTaskCrudRepository.findById(id);
        return taskEntityOptional.map(taskMapper::toTask).orElseGet(Task::new);
    }

    @Override
    public Iterable<Task> findByUserId(Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return taskMapper.toTasks(iTaskCrudRepository.findByUserEntity(userEntity));
    }

    @Override
    public Iterable<Task> findByStatus(Integer userId, Status status) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return taskMapper.toTasks(iTaskCrudRepository.findByUserEntityAndStatus(userEntity, status));
    }

    @Override
    public Iterable<Task> findByNameOrDescription(Integer userId, String text) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        Iterable<TaskEntity> allTasks = iTaskCrudRepository.findByUserEntity(userEntity);
        if (text == null || text.isEmpty()) {
            return taskMapper.toTasks(allTasks);
        } else {
            String searchtext = text.toLowerCase();
            List<TaskEntity> matchingTasks = new ArrayList<>();
            for (TaskEntity taskEntity : allTasks) {
                String lowerCaseName = taskEntity.getName().toLowerCase();
                String lowerCaseDescription = taskEntity.getDescription().toLowerCase();
                if (lowerCaseName.startsWith(searchtext) || lowerCaseDescription.startsWith(searchtext)) {
                    matchingTasks.add(taskEntity);
                }
            }
            return taskMapper.toTasks(matchingTasks);
        }
    }

    @Override
    public void updateStatusById(Integer id, Status status) {
        iTaskCrudRepository.updateStatusById(id, status);
    }

    @Override
    public void deleteById(Integer id) {
        iTaskCrudRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        iTaskCrudRepository.deleteById(id);
    }
}
