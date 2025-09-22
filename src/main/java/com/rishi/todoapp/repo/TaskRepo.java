package com.rishi.todoapp.repo;

import com.rishi.todoapp.enums.Enum;
import com.rishi.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.dueDate = :dueDate")
    Optional<Task> findByUserIdAndDueDate(@Param("userId") Long userId, @Param("dueDate") LocalDateTime dueDate);


    @Query(value = """
            SELECT * FROM task WHERE status = 'PENDING'
             AND due_date >= NOW() AND user = :userId
            """, nativeQuery = true)
    List<Task> findInboxTasks(@Param("userId") Long userId);

    @Query(value = """
            SELECT * FROM task WHERE DATE(due_date) = CURDATE()
            AND user = :userId
            AND status NOT IN (:status)
            """, nativeQuery = true)
    List<Task> findTodayTasks(@Param("userId") Long userId, List<String> status);

    @Query(value = """
            SELECT * FROM task WHERE DATE(due_date) > CURDATE() AND user = :userId
            AND status NOT IN (:status)
            """, nativeQuery = true)
    List<Task> findUpcomingTasks(@Param("userId") Long userId, List<String> status);

    @Query(value = """
            SELECT * FROM task WHERE status = :status AND user = :userId
            """, nativeQuery = true)
    List<Task> findTasksByStatus(@Param("status") String status, @Param("userId") Long userId);

    @Query(value = """
            SELECT * FROM task WHERE DATE(due_date) < CURDATE() AND user = :userId
            AND status NOT IN (:status)
            """, nativeQuery = true)
    List<Task> findPastTasks(Long userId, List<String> tasks);

    @Query(value = """
            SELECT * FROM task WHERE id = :taskId AND status <> :status AND user = :userId
            """, nativeQuery = true)
    Optional<Task> findByIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId, @Param("status") String status);

    @Query(value = """
            SELECT * FROM task WHERE id = :taskId AND user = :userId
            """, nativeQuery = true)
    Optional<Task> findByIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);
}
