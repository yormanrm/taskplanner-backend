package com.backend.taskplanner.infrastructure.adapter;

import com.backend.taskplanner.domain.model.User;
import com.backend.taskplanner.domain.ports.IUserRepository;
import com.backend.taskplanner.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserCrudRepositoryImpl implements IUserRepository {
    private final IUserCrudRepository iUserCrudRepository;
    private final UserMapper userMapper;

    public UserCrudRepositoryImpl(IUserCrudRepository iUserCrudRepository, UserMapper userMapper) {
        this.iUserCrudRepository = iUserCrudRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        return userMapper.toUser(iUserCrudRepository.save(userMapper.toUserEntity(user)));
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(iUserCrudRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User with email " + email + " not found")));
    }
}
