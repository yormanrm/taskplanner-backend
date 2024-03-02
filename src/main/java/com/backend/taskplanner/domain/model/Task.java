package com.backend.taskplanner.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Boolean archived;
    private Double percentage;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private Integer userId;

    public Task() {
        activities = new ArrayList<>();
        updatePercentage();
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
        updatePercentage();
    }

    private void updatePercentage() {
        if (activities.isEmpty()) {
            percentage = (status == Status.COMPLETED) ? 100.0 : 0.0;
//            if (percentage == 100.00) {
//                status = Status.COMPLETED;
//            }
        } else {
            long completedActivities = activities.stream().filter(activity -> activity.getStatus() == Status.COMPLETED).count();
            percentage = (double) completedActivities / activities.size() * 100;
//            if (percentage == 100.00) {
//                status = Status.COMPLETED;
//            } else {
//                status = Status.ONGOING;
//            }
        }
    }

}
