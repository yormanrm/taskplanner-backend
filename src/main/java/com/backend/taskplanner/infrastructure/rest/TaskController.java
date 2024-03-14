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

    @PostMapping("/save")
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

    @GetMapping("/get/all")
    public ResponseEntity<Iterable<Task>> findByUserId(@RequestParam Boolean archived, HttpServletRequest request) {
        try {
            Claims claims = JWTValid(request);
            Integer userId = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(taskService.findByUserId(userId, archived), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/byId")
    public ResponseEntity<Task> findById(@RequestParam Integer id) {
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

    @GetMapping("/get/byStatus")
    public ResponseEntity<Iterable<Task>> findByStatus(@RequestParam String status, @RequestParam Boolean archived, HttpServletRequest request) {
        try {
            Status statusValue = Status.valueOf(status);
            Claims claims = JWTValid(request);
            Integer userId = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(taskService.findByStatus(userId, statusValue, archived), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/bySearch")
    public ResponseEntity<Iterable<Task>> findByNameOrDescription(@RequestParam String search, @RequestParam Boolean archived, HttpServletRequest request) {
        try {
            Claims claims = JWTValid(request);
            Integer userId = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(taskService.findByNameOrDescription(userId, search, archived), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/byDatesRange")
    public ResponseEntity<Iterable<Task>> findByDatesRange(HttpServletRequest request, @RequestParam String startDate, @RequestParam String endDate, @RequestParam Boolean archived) {
        try {
            Claims claims = JWTValid(request);
            Integer userId = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(taskService.findByDatesRange(userId, startDate, endDate, archived), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateStatus")
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

    @PostMapping("/updateArchived")
    public ResponseEntity updateArchivedById(@RequestParam Integer id, @RequestParam Boolean archived) {
        try {
            taskService.updateArchivedById(id, archived);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
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