package com.backend.taskplanner.domain.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Task {

    private Integer id;
    private String name;
    private String description;
    private Status status;
    private List<Activity> activities;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Integer userId;

    public Task() {
        activities = new ArrayList<>();
    }

}
