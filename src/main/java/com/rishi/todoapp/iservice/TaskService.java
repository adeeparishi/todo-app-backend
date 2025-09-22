package com.rishi.todoapp.iservice;

import com.rishi.todoapp.dto.request.TaskRequest;
import com.rishi.todoapp.dto.response.TaskResponse;
import com.rishi.todoapp.enums.Enum;

import java.util.List;

public interface TaskService {

    TaskResponse addTask(TaskRequest taskRequest);

    List<TaskResponse> getTaskByTabType(String tabType, Long userId);

    TaskResponse updateTask(TaskRequest taskRequest);

    TaskResponse updateTaskStatus(Long taskId, Long userid, Enum.Status status);

}
