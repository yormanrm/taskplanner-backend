package com.backend.taskplanner.domain.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
