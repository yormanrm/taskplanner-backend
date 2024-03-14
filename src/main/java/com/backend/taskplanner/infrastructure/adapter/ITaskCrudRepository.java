package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.infrastructure.entity.TaskEntity;
import com.backend.taskplanner.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


public interface ITaskCrudRepository extends CrudRepository<TaskEntity, Integer> {
    @Query("SELECT t FROM TaskEntity t WHERE t.userEntity = :userEntity AND t.archived = :archived")
    Iterable<TaskEntity> findByUserEntity(@Param("userEntity") UserEntity userEntity, @Param("archived") Boolean archived);

    @Query("SELECT t FROM TaskEntity t WHERE t.userEntity = :userEntity AND t.archived = :archived AND t.status = :status")
    Iterable<TaskEntity> findByUserEntityAndStatus(@Param("userEntity") UserEntity userEntity, @Param("status") Status status, @Param("archived") Boolean archived);

    @Query("SELECT t FROM TaskEntity t WHERE t.userEntity = :userEntity AND t.archived = :archived AND (t.name LIKE %:text% OR t.description LIKE %:text%)")
    Iterable<TaskEntity> findByNameOrDescription(@Param("userEntity") UserEntity userEntity, @Param("text") String text, @Param("archived") Boolean archived);

    @Query("SELECT t FROM TaskEntity t WHERE t.dateCreated BETWEEN :startDate AND :endDate AND t.userEntity = :userEntity AND t.archived = :archived")
    Iterable<TaskEntity> findByDatesRange(UserEntity userEntity, LocalDateTime startDate, LocalDateTime endDate, Boolean archived);

    @Transactional
    @Modifying
    @Query("UPDATE TaskEntity t SET t.status = :status WHERE t.id = :id")
    void updateStatusById(Integer id, Status status);

    @Transactional
    @Modifying
    @Query("UPDATE TaskEntity t SET t.archived = :archived WHERE t.id = :id")
    void updateArchivedById(Integer id, Boolean archived);
}
