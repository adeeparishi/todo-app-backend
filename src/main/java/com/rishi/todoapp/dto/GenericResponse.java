package com.rishi.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
public class GenericResponse<T> {

    @NonNull
    private Boolean status;

    private String message;

    private String errorType;

    @Builder.Default
    private Long timestamp = System.currentTimeMillis();

    private T data;

    @Builder.Default
    private Map<String, String> errors = Collections.emptyMap();

    private long totalPages;

    private long totalElements;

    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .message("Response Success")
                .data(data)
                .errorType("NONE")
                .status(true)
                .build();
    }

    public static <T> GenericResponse<T> success(String message, T data) {
        return GenericResponse.<T>builder()
                .message(message)
                .data(data)
                .errorType("NONE")
                .status(true)
                .build();
    }

    public static <T> GenericResponse<T> success(String message, T data, long totalElements, long totalPages) {
        return GenericResponse.<T>builder()
                .message(message)
                .data(data)
                .errorType("NONE")
                .status(true)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
    }

    public static <T> GenericResponse<T> error(String errorType, String message) {
        return GenericResponse.<T>builder()
                .message(message)
                .errorType(errorType)
                .status(false)
                .build();
    }
}
