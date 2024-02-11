package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.infrastructure.entity.TaskEntity;
import com.backend.taskplanner.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface ITaskCrudRepository extends CrudRepository<TaskEntity, Integer> {
    Iterable<TaskEntity> findByUserEntity(UserEntity userEntity);

    Iterable<TaskEntity> findByStatus(Status status);

//    Iterable<TaskEntity> findByNameOrDescription(String text);

    @Transactional
    @Modifying
    @Query("UPDATE TaskEntity t SET t.status = :status WHERE t.id = :id")
    void updateStatusById(Integer id, Status status);

}
