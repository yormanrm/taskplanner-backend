package com.backend.taskplanner.infrastructure.entity;

import com.backend.taskplanner.domain.model.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime dateUpdated;
    @ManyToOne
    private TaskEntity taskEntity;
}
