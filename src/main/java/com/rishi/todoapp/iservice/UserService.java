package com.rishi.todoapp.iservice;

import com.rishi.todoapp.dto.request.UserRequest;
import com.rishi.todoapp.dto.response.UserResponse;
import com.rishi.todoapp.model.User;

public interface UserService {

    UserResponse register(UserRequest request);

    UserResponse login(UserRequest request);

    UserResponse update(UserRequest request);

    User findById(Long id);

    void save(User user);
}
