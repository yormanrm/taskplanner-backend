package com.backend.taskplanner.application;

import com.backend.taskplanner.domain.model.User;
import com.backend.taskplanner.domain.ports.IUserRepository;

public class UserService {
    private final IUserRepository iUserRepository;

    public UserService(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public User save(User user) {
        return this.iUserRepository.save(user);
    }

    public User findByEmail(String email) {
        return iUserRepository.findByEmail(email);
    }
}
