package com.backend.taskplanner.infrastructure.rest;

import com.backend.taskplanner.application.UserService;
import com.backend.taskplanner.domain.model.User;
import com.backend.taskplanner.infrastructure.dto.JWTClient;
import com.backend.taskplanner.infrastructure.dto.UserDTO;
import com.backend.taskplanner.infrastructure.jwt.JWTGenerator;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTClient> login(@RequestBody UserDTO userDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userService.findByEmail(userDTO.email());
            String token = jwtGenerator.getToken(userDTO.email(), user.getId());
            JWTClient jwtClient = new JWTClient(token);
            return new ResponseEntity<>(jwtClient, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
