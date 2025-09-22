package com.rishi.todoapp.serviceImpl;

import com.rishi.todoapp.dto.request.UserRequest;
import com.rishi.todoapp.dto.response.UserResponse;
import com.rishi.todoapp.enums.Enum;
import com.rishi.todoapp.exception.AlreadyExistException;
import com.rishi.todoapp.exception.BusinessException;
import com.rishi.todoapp.exception.NotFoundException;
import com.rishi.todoapp.iservice.UserService;
import com.rishi.todoapp.model.User;
import com.rishi.todoapp.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public UserResponse register(UserRequest request) {

        log.info("request {} ", request.toString());

        Optional<User> existingUser = userRepo.findByEmail(request.getEmail());
        existingUser.ifPresent(u -> {
            if (u.getStatus().equals(Enum.Status.BANNED))
                throw new BusinessException("User was Banned from this portal, please contact support team");
            if (u.getStatus().equals(Enum.Status.ACTIVE))
                throw new AlreadyExistException("Account already exist, Please do login");
        });

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setStatus(Enum.Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        log.info("To user model {}", user);

        // Save the user and return the response
        return toResponse(userRepo.save(user));
    }


    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .id(user.getId())
                .createdAt(user.getCreatedAt())
                .name(user.getName())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    public UserResponse login(UserRequest request) {
        User user = userRepo.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new NotFoundException("Incorrect Username or Password"));
        log.info("User {}", user);
        return toResponse(user);
    }

    @Override
    public UserResponse update(UserRequest request) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }
}
