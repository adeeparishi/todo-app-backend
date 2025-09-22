package com.rishi.todoapp.serviceImpl;

import com.rishi.todoapp.dto.request.TaskRequest;
import com.rishi.todoapp.dto.response.TaskResponse;
import com.rishi.todoapp.enums.Enum;
import com.rishi.todoapp.exception.AlreadyExistException;
import com.rishi.todoapp.exception.BusinessException;
import com.rishi.todoapp.exception.NotFoundException;
import com.rishi.todoapp.iservice.TaskService;
import com.rishi.todoapp.iservice.UserService;
import com.rishi.todoapp.mapper.TaskMapper;
import com.rishi.todoapp.model.Task;
import com.rishi.todoapp.model.User;
import com.rishi.todoapp.repo.TaskRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;
    private final UserService userService;

    @Override
    public TaskResponse addTask(TaskRequest taskRequest) {

        Optional<Task> existingTask = taskRepo.findByUserIdAndDueDate(
                taskRequest.getUserId(), taskRequest.getDueDate()
        );

        existingTask.ifPresent(t -> {
            throw new AlreadyExistException("Task Collision!");
        });

        User user = userService.findById(taskRequest.getUserId());
        Task task = toModel(taskRequest);
        task.setUser(user);
        user.addTask(task);

        return toResponse(
                taskRepo.save(task)
        );
    }

    private Task toModel(TaskRequest taskRequest) {
        return Task.builder()
                .title(taskRequest.getTitle())
                .createdAt(LocalDateTime.now())
                .description(taskRequest.getDescription())
                .dueDate(taskRequest.getDueDate())
                .priority(taskRequest.getPriority())
                .status(Enum.Status.PENDING)
                .build();
    }

    @Override
    public List<TaskResponse> getTaskByTabType(String tabType, Long userId) {

        List<Task> tasks = new ArrayList<>();
        if (tabType.equalsIgnoreCase("inbox")) {
            tasks = taskRepo.findInboxTasks(userId);
        }

        if (tabType.equalsIgnoreCase("today")) {
            tasks = taskRepo.findTodayTasks(userId, List.of(Enum.Status.COMPLETED.name(), Enum.Status.DELETED.name()));
        }

        if (tabType.equalsIgnoreCase("upcoming")) {
            tasks = taskRepo.findUpcomingTasks(userId, List.of(Enum.Status.COMPLETED.name(), Enum.Status.DELETED.name()));
        }
        if (tabType.equalsIgnoreCase("completed")) {
            tasks = taskRepo.findTasksByStatus(Enum.Status.COMPLETED.name(), userId);
        }
        if (tabType.equalsIgnoreCase("past")) {
            tasks = taskRepo.findPastTasks(userId, List.of(Enum.Status.DELETED.name()));
        }
        return tasks.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TaskResponse toResponse(Task t) {
        return TaskResponse.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .dueDate(t.getDueDate())
                .createdAt(t.getCreatedAt())
                .userId(t.getUser().getId())
                .priority(t.getPriority())
                .build();
    }

    @Override
    public TaskResponse updateTask(TaskRequest taskRequest) {
        return null;
    }

    @Override
    public TaskResponse updateTaskStatus(Long taskId, Long userId, Enum.Status status) {
        Task task = taskRepo.findByIdAndUserId(taskId, userId, Enum.Status.DELETED.name())
                .orElseThrow(() -> new NotFoundException("Task not found!"));
        task.setStatus(status);
        return toResponse(
                taskRepo.save(task)
        );
    }
}
