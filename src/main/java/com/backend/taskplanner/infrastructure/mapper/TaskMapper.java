package com.backend.taskplanner.infrastructure.mapper;

import com.backend.taskplanner.domain.model.Activity;
import com.backend.taskplanner.domain.model.Task;
import com.backend.taskplanner.infrastructure.entity.ActivityEntity;
import com.backend.taskplanner.infrastructure.entity.TaskEntity;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mappings({@Mapping(source = "id", target = "id"), @Mapping(source = "name", target = "name"), @Mapping(source = "description", target = "description"), @Mapping(source = "status", target = "status"), @Mapping(source = "dateCreated", target = "dateCreated"), @Mapping(source = "dateUpdated", target = "dateUpdated"), @Mapping(source = "userEntity.id", target = "userId")})
    Task toTask(TaskEntity taskEntity);

    @AfterMapping
    default void mapActivities(TaskEntity taskEntity, @MappingTarget Task task) {
        task.setActivities(mapActivityEntitiesToActivities(taskEntity.getActivities()));
    }

    default List<Activity> mapActivityEntitiesToActivities(List<ActivityEntity> activityEntities) {
        return activityEntities.stream().map(activityEntity -> {
            Activity activity = new Activity();
            activity.setId(activityEntity.getId());
            activity.setDescription(activityEntity.getDescription());
            activity.setStatus(activityEntity.getStatus());
            activity.setDateCreated(activityEntity.getDateCreated());
            activity.setDateUpdated(activityEntity.getDateUpdated());
            activity.setTaskId(activityEntity.getTaskEntity().getId());
            return activity;
        }).collect(Collectors.toList());
    }

    Iterable<Task> toTasks(Iterable<TaskEntity> taskEntities);

    @InheritInverseConfiguration
    TaskEntity toTaskEntity(Task task);

    Iterable<TaskEntity> toTaskEntities(Iterable<Task> tasks);

}
