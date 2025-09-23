package com.rishi.todoapp.model;

import com.rishi.todoapp.enums.Enum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Enum.Priority priority;

    @Enumerated(EnumType.STRING)
    private Enum.Status status;

    private LocalDateTime completedOn;
    private LocalDateTime updatedAt;
}
