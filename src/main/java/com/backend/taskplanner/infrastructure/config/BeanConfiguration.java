package com.backend.taskplanner.infrastructure.config;

import com.backend.taskplanner.application.ActivityService;
import com.backend.taskplanner.application.TaskService;
import com.backend.taskplanner.application.UserService;
import com.backend.taskplanner.domain.ports.IActivityRepository;
import com.backend.taskplanner.domain.ports.ITaskRepository;
import com.backend.taskplanner.domain.ports.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public UserService userService(IUserRepository iUserRepository) {
        return new UserService(iUserRepository);
    }

    @Bean
    public TaskService taskService(ITaskRepository iTaskRepository) {
        return new TaskService(iTaskRepository);
    }

    @Bean
    public ActivityService activityService(IActivityRepository iActivityRepository) {
        return new ActivityService(iActivityRepository);
    }
}
