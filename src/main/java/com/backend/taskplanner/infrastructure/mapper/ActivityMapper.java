package com.backend.taskplanner.infrastructure.mapper;

import com.backend.taskplanner.domain.model.Activity;
import com.backend.taskplanner.infrastructure.entity.ActivityEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @Mappings({@Mapping(source = "id", target = "id"), @Mapping(source = "description", target = "description"), @Mapping(source = "status", target = "status"), @Mapping(source = "dateCreated", target = "dateCreated"), @Mapping(source = "dateUpdated", target = "dateUpdated"), @Mapping(source = "taskEntity.id", target = "taskId"),})
    Activity toActivity(ActivityEntity activityEntity);

    Iterable<Activity> toActivities(Iterable<ActivityEntity> activityEntities);

    @InheritInverseConfiguration
    ActivityEntity toActivityEntity(Activity activity);

    Iterable<ActivityEntity> toActivityEntities(Iterable<Activity> activities);

}
