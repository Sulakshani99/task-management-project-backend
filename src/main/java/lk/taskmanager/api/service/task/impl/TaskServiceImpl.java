package lk.taskmanager.api.service.task.impl;

import lk.taskmanager.api.dto.request.TaskCreateRequest;
import lk.taskmanager.api.dto.request.TaskUpdateRequest;
import lk.taskmanager.api.dto.response.CommonResponse;
import lk.taskmanager.api.dto.response.TaskResponse;
import lk.taskmanager.api.dto.response.UserResponse;
import lk.taskmanager.api.enums.ActiveStatus;
import lk.taskmanager.api.enums.TaskStatus;
import lk.taskmanager.api.model.Task;
import lk.taskmanager.api.model.User;
import lk.taskmanager.api.repository.TaskRepository;
import lk.taskmanager.api.repository.UserRepository;
import lk.taskmanager.api.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommonResponse createTask(TaskCreateRequest request, String creatorEmail) {
        User creator = findUserByEmail(creatorEmail);

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .dueDate(request.getDueDate())
                .activeStatus(ActiveStatus.ACTIVE)
                .createdBy(creator)
                .build();

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignee(assignee);
        }

        Task saved = taskRepository.save(task);
        log.info("Task created: {} by {}", saved.getId(), creatorEmail);
        return CommonResponse.success("Task created successfully", toTaskResponse(saved));
    }

    @Override
    @Transactional
    public CommonResponse updateTask(Long taskId, TaskUpdateRequest request, String userEmail) {
        Task task = findActiveTask(taskId);

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            task.setAssignee(assignee);
        }

        Task updated = taskRepository.save(task);
        log.info("Task updated: {} by {}", taskId, userEmail);
        return CommonResponse.success("Task updated successfully", toTaskResponse(updated));
    }

    @Override
    @Transactional
    public CommonResponse deleteTask(Long taskId, String userEmail) {
        Task task = findActiveTask(taskId);
        task.setActiveStatus(ActiveStatus.DELETED);
        taskRepository.save(task);
        log.info("Task deleted: {} by {}", taskId, userEmail);
        return CommonResponse.success("Task deleted successfully");
    }

    @Override
    public TaskResponse getTaskById(Long taskId) {
        return toTaskResponse(findActiveTask(taskId));
    }

    @Override
    public Page<TaskResponse> getAllTasks(Pageable pageable) {
        return taskRepository.findAllByActiveStatus(ActiveStatus.ACTIVE, pageable)
                .map(this::toTaskResponse);
    }

    @Override
    public Page<TaskResponse> getMyTasks(String userEmail, Pageable pageable) {
        User user = findUserByEmail(userEmail);
        return taskRepository.findAllByCreatedByAndActiveStatus(user, ActiveStatus.ACTIVE, pageable)
                .map(this::toTaskResponse);
    }

    @Override
    public Page<TaskResponse> getTasksAssignedToMe(String userEmail, Pageable pageable) {
        User user = findUserByEmail(userEmail);
        return taskRepository.findAllByAssigneeAndActiveStatus(user, ActiveStatus.ACTIVE, pageable)
                .map(this::toTaskResponse);
    }

    @Override
    public Page<TaskResponse> searchTasks(String keyword, Pageable pageable) {
        return taskRepository.searchTasks(keyword, ActiveStatus.ACTIVE, pageable)
                .map(this::toTaskResponse);
    }

    @Override
    @Transactional
    public CommonResponse updateTaskStatus(Long taskId, TaskStatus newStatus, String userEmail) {
        Task task = findActiveTask(taskId);
        task.setStatus(newStatus);
        taskRepository.save(task);
        log.info("Task {} status updated to {} by {}", taskId, newStatus, userEmail);
        return CommonResponse.success("Task status updated", toTaskResponse(task));
    }

    // ---- Helpers ----

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    private Task findActiveTask(Long taskId) {
        return taskRepository.findByIdAndActiveStatus(taskId, ActiveStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }

    private TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .assignee(task.getAssignee() != null ? toUserResponse(task.getAssignee()) : null)
                .createdBy(toUserResponse(task.getCreatedBy()))
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}
