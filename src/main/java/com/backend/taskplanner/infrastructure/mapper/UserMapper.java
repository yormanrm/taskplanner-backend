package com.backend.taskplanner.infrastructure.mapper;

import com.backend.taskplanner.domain.model.User;
import com.backend.taskplanner.infrastructure.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({@Mapping(source = "id", target = "id"), @Mapping(source = "firstname", target = "firstname"), @Mapping(source = "lastname", target = "lastname"), @Mapping(source = "email", target = "email"), @Mapping(source = "password", target = "password"), @Mapping(source = "dateCreated", target = "dateCreated"), @Mapping(source = "dateUpdated", target = "dateUpdated")})
    User toUser(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity toUserEntity(User user);

}
