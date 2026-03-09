package lk.taskmanager.api.dto.response;

import lk.taskmanager.api.enums.TaskPriority;
import lk.taskmanager.api.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private UserResponse assignee;
    private UserResponse createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
