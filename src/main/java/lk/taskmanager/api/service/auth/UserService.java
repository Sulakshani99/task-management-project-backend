package lk.taskmanager.api.service.auth;

import lk.taskmanager.api.dto.request.UserSignupRequest;
import lk.taskmanager.api.dto.response.CommonResponse;
import lk.taskmanager.api.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    CommonResponse signup(UserSignupRequest request);
    UserResponse getUserProfile(String email);
    List<UserResponse> getAllUsers();
}
