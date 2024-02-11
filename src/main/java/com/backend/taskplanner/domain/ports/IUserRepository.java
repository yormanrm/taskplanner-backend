package com.backend.taskplanner.domain.ports;

import com.backend.taskplanner.domain.model.User;

public interface IUserRepository {
    User save(User user);

    User findByEmail(String email);
}