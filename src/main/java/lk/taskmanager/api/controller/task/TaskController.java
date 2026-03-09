package lk.taskmanager.api.controller.task;

import lk.taskmanager.api.dto.request.TaskCreateRequest;
import lk.taskmanager.api.dto.request.TaskUpdateRequest;
import lk.taskmanager.api.dto.response.CommonResponse;
import lk.taskmanager.api.dto.response.TaskResponse;
import lk.taskmanager.api.enums.TaskStatus;
import lk.taskmanager.api.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Log4j2
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<CommonResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request,
            Principal principal) {
        return ResponseEntity.ok(taskService.createTask(request, principal.getName()));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<CommonResponse> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskUpdateRequest request,
            Principal principal) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request, principal.getName()));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<CommonResponse> deleteTask(
            @PathVariable Long taskId,
            Principal principal) {
        return ResponseEntity.ok(taskService.deleteTask(taskId, principal.getName()));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<TaskResponse>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(taskService.getAllTasks(pageable));
    }

    @GetMapping("/my")
    public ResponseEntity<Page<TaskResponse>> getMyTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(taskService.getMyTasks(principal.getName(), pageable));
    }

    @GetMapping("/assigned")
    public ResponseEntity<Page<TaskResponse>> getTasksAssignedToMe(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(taskService.getTasksAssignedToMe(principal.getName(), pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TaskResponse>> searchTasks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(taskService.searchTasks(keyword, pageable));
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<CommonResponse> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status,
            Principal principal) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, status, principal.getName()));
    }
}
