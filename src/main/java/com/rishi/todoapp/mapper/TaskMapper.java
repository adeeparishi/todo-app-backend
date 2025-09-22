package com.rishi.todoapp.mapper;

import com.rishi.todoapp.dto.request.TaskRequest;
import com.rishi.todoapp.dto.response.TaskResponse;
import com.rishi.todoapp.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toModel(TaskRequest taskRequest);

    @Mappings({
//            @Mapping(source = "user.id", target = "userId"),
//            @Mapping(source = "id", target = "id")
    })
    TaskResponse toResponse(Task task);

}
