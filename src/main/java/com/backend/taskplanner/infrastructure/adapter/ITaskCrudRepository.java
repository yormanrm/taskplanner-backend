package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.infrastructure.entity.TaskEntity;
import com.backend.taskplanner.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


public interface ITaskCrudRepository extends CrudRepository<TaskEntity, Integer> {
    Iterable<TaskEntity> findByUserEntity(UserEntity userEntity);

    Iterable<TaskEntity> findByUserEntityAndStatus(UserEntity userEntity, Status status);

    @Query("SELECT t FROM TaskEntity t WHERE t.dateCreated BETWEEN :startDate AND :endDate AND t.userEntity = :userEntity")
    Iterable<TaskEntity> findByDatesRange(UserEntity userEntity, LocalDateTime startDate, LocalDateTime endDate);

    @Transactional
    @Modifying
    @Query("UPDATE TaskEntity t SET t.status = :status WHERE t.id = :id")
    void updateStatusById(Integer id, Status status);

    @Transactional
    @Modifying
    @Query("UPDATE TaskEntity t SET t.archived = :archived WHERE t.id = :id")
    void updateArchivedById(Integer id, Boolean archived);
}
