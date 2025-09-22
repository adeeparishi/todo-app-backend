package com.rishi.todoapp.mapper;

import com.rishi.todoapp.dto.response.UserResponse;
import com.rishi.todoapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}
