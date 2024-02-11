package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserCrudRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}
