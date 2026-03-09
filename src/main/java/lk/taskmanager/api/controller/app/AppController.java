package lk.taskmanager.api.controller.app;

import lk.taskmanager.api.dto.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/application")
public class AppController {

    @GetMapping("/version")
    public ResponseEntity<CommonResponse> getVersion() {
        return ResponseEntity.ok(CommonResponse.success("Task Management API",
                Map.of("version", "1.0.0", "name", "Task Management System")));
    }

    @GetMapping("/health")
    public ResponseEntity<CommonResponse> health() {
        return ResponseEntity.ok(CommonResponse.success("Service is healthy"));
    }
}
