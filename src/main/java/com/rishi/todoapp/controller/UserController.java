package com.rishi.todoapp.controller;

import com.rishi.todoapp.dto.GenericResponse;
import com.rishi.todoapp.dto.request.UserRequest;
import com.rishi.todoapp.dto.response.UserResponse;
import com.rishi.todoapp.iservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<UserResponse>> register(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(
                GenericResponse.success(
                        userService.register(userRequest)
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<UserResponse>> login(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(
                GenericResponse.success(
                        userService.login(userRequest)
                )
        );
    }
}
