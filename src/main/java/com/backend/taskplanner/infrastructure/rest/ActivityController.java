package com.backend.taskplanner.infrastructure.rest;

import com.backend.taskplanner.application.ActivityService;
import com.backend.taskplanner.domain.model.Activity;
import com.backend.taskplanner.domain.model.Task;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.taskplanner.infrastructure.jwt.JWTValidate.JWTValid;

@RestController
@RequestMapping("/activities")
@CrossOrigin(origins = "http://localhost:4200/")
@Slf4j
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/saveActivity")
    public ResponseEntity<Activity> save(@RequestBody Activity activity) {
        try {
            return new ResponseEntity<>(activityService.save(activity), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}