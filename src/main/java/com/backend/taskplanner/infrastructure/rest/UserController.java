package com.backend.taskplanner.infrastructure.rest;

import com.backend.taskplanner.application.UserService;
import com.backend.taskplanner.domain.model.Task;
import com.backend.taskplanner.domain.model.User;
import com.backend.taskplanner.infrastructure.dto.JWTClient;
import com.backend.taskplanner.infrastructure.dto.UserDTO;
import com.backend.taskplanner.infrastructure.jwt.JWTGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userService.save(user);
            if(newUser.getId() != null){
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JWTClient> login(@RequestBody UserDTO userDTO) {
        try {
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                User user = userService.findByEmail(userDTO.email());
                String token = jwtGenerator.getToken(userDTO.email(), user.getId());
                String name = user.getFirstname() + " " +user.getLastname();
                JWTClient jwtClient = new JWTClient(token, name, user.getEmail());
                return new ResponseEntity<>(jwtClient, HttpStatus.OK);
            } catch (AuthenticationException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
