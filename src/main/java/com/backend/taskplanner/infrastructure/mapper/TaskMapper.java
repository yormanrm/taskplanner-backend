package com.backend.taskplanner.infrastructure.mapper;

import com.backend.taskplanner.domain.model.Task;
import com.backend.taskplanner.infrastructure.entity.TaskEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mappings({@Mapping(source = "id", target = "id"), @Mapping(source = "name", target = "name"), @Mapping(source = "description", target = "description"), @Mapping(source = "status", target = "status"), @Mapping(source = "activities", target = "activities"), @Mapping(source = "dateCreated", target = "dateCreated"), @Mapping(source = "dateUpdated", target = "dateUpdated"), @Mapping(source = "userEntity.id", target = "userId"),})
    Task toTask(TaskEntity taskEntity);

    Iterable<Task> toTasks(Iterable<TaskEntity> taskEntities);

    @InheritInverseConfiguration
    TaskEntity toTaskEntity(Task task);

    Iterable<TaskEntity> toTaskEntities(Iterable<Task> tasks);

}
