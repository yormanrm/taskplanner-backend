package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.infrastructure.entity.ActivityEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface IActivityCrudRepository extends CrudRepository<ActivityEntity, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE ActivityEntity a SET a.status = :status WHERE a.id = :id")
    void updateStatusById(Integer id, Status status);
}
