package com.rishi.todoapp.dto.request;

import com.rishi.todoapp.enums.Enum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskRequest {

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private Long userId;
    private Enum.Priority priority;

}
