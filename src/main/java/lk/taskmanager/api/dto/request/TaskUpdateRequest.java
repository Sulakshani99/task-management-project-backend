package lk.taskmanager.api.dto.request;

import lk.taskmanager.api.enums.TaskPriority;
import lk.taskmanager.api.enums.TaskStatus;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class TaskUpdateRequest {

    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    private String description;

    private TaskPriority priority;

    private TaskStatus status;

    private LocalDate dueDate;

    private Long assigneeId;
}
