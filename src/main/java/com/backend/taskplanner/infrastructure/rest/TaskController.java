package com.backend.taskplanner.infrastructure.rest;

import com.backend.taskplanner.application.TaskService;
import com.backend.taskplanner.domain.model.Status;
import com.backend.taskplanner.domain.model.Task;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.backend.taskplanner.infrastructure.jwt.JWTValidate.JWTValid;

@RestController
@Slf4j
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:4200/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/saveTask")
    public ResponseEntity<Task> save(@RequestBody Task task, HttpServletRequest request) {
        try {
            Claims claims = JWTValid(request);
            Integer userId = Integer.parseInt(claims.get("id").toString());
            task.setUserId(userId);
            return new ResponseEntity<>(taskService.save(task), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byTaskId/{id}")
    public ResponseEntity<Task> findById(@PathVariable Integer id) {
        try {
            Task task = taskService.findById(id);
            if (task.getId() != null) {
                return new ResponseEntity<>(task, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(task, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byUser")
    public ResponseEntity<Iterable<Task>> findByUserId(HttpServletRequest request) {
        try {
            Claims claims = JWTValid(request);
            Integer userId = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(taskService.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byUser/byStatus")
    public ResponseEntity<Iterable<Task>> findByStatus(@RequestParam String status, HttpServletRequest request) {
        try {
            Status statusValue = Status.valueOf(status);
            Claims claims = JWTValid(request);
            Integer userId = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(taskService.findByStatus(userId, statusValue), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byUser/search")
    public ResponseEntity<Iterable<Task>> findByNameOrDescription(@RequestParam String search, HttpServletRequest request) {
        try {
            Claims claims = JWTValid(request);
            Integer userId = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(taskService.findByNameOrDescription(userId, search), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateTaskStatus")
    public ResponseEntity updateStateById(@RequestParam Integer id, @RequestParam String status) {
        try {
            Status statusValue = Status.valueOf(status);
            taskService.updateStatusById(id, statusValue);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<HttpStatus> deleteById(@RequestParam Integer id) {
        try {
            taskService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
