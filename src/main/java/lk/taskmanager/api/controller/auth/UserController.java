package lk.taskmanager.api.controller.auth;

import lk.taskmanager.api.dto.request.UserSignupRequest;
import lk.taskmanager.api.dto.response.CommonResponse;
import lk.taskmanager.api.dto.response.UserResponse;
import lk.taskmanager.api.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> signup(@Valid @RequestBody UserSignupRequest request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getUserProfile(principal.getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
