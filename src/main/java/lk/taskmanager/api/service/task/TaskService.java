package lk.taskmanager.api.service.task;

import lk.taskmanager.api.dto.request.TaskCreateRequest;
import lk.taskmanager.api.dto.request.TaskUpdateRequest;
import lk.taskmanager.api.dto.response.CommonResponse;
import lk.taskmanager.api.dto.response.TaskResponse;
import lk.taskmanager.api.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    CommonResponse createTask(TaskCreateRequest request, String creatorEmail);
    CommonResponse updateTask(Long taskId, TaskUpdateRequest request, String userEmail);
    CommonResponse deleteTask(Long taskId, String userEmail);
    TaskResponse getTaskById(Long taskId);
    Page<TaskResponse> getAllTasks(Pageable pageable);
    Page<TaskResponse> getMyTasks(String userEmail, Pageable pageable);
    Page<TaskResponse> getTasksAssignedToMe(String userEmail, Pageable pageable);
    Page<TaskResponse> searchTasks(String keyword, Pageable pageable);
    CommonResponse updateTaskStatus(Long taskId, TaskStatus newStatus, String userEmail);
}
