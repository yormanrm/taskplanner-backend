package com.backend.taskplanner.domain.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private Integer id;
    private String description;
    private Status status;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Integer taskId;
}
