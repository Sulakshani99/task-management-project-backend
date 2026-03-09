package lk.taskmanager.api.dto.request;

import lk.taskmanager.api.enums.TaskPriority;
import lk.taskmanager.api.enums.TaskStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class TaskCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    private String description;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    private TaskStatus status;

    private LocalDate dueDate;

    private Long assigneeId;
}
