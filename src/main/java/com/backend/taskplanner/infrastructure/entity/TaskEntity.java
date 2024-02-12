package com.backend.taskplanner.infrastructure.entity;

import com.backend.taskplanner.domain.model.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;
    @ManyToOne
    private UserEntity userEntity;
    @OneToMany(mappedBy = "taskEntity", cascade = CascadeType.ALL)
    private List<ActivityEntity> activities;
}
