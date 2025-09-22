package com.rishi.todoapp.controller;

import com.rishi.todoapp.dto.GenericResponse;
import com.rishi.todoapp.dto.request.TaskRequest;
import com.rishi.todoapp.dto.response.TaskResponse;
import com.rishi.todoapp.enums.Enum;
import com.rishi.todoapp.iservice.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponse<TaskResponse>> add(@RequestBody TaskRequest request) {
        return ResponseEntity.ok(
                GenericResponse.success(
                        taskService.addTask(request)
                )
        );
    }

    @GetMapping("/get-by")
    public ResponseEntity<GenericResponse<List<TaskResponse>>> get(@RequestParam("tabType") String tabType,
                                                     @RequestParam("userId") Long userId) {
        return ResponseEntity.ok(
                GenericResponse.success(
                        taskService.getTaskByTabType(tabType, userId)
                )
        );
    }

    @PutMapping("/change-status")
    public ResponseEntity<GenericResponse<TaskResponse>> updateTask(
            @RequestParam("taskId") Long taskId,
            @RequestParam("userId") Long userId,
            @RequestParam("status") Enum.Status status) {
        return ResponseEntity.ok(
                GenericResponse.success(
                        taskService.updateTaskStatus(taskId, userId, status)
                )
        );
    }

}
