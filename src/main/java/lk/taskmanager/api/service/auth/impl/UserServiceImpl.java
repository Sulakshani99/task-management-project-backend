package lk.taskmanager.api.service.auth.impl;

import lk.taskmanager.api.dto.request.UserSignupRequest;
import lk.taskmanager.api.dto.response.CommonResponse;
import lk.taskmanager.api.dto.response.UserResponse;
import lk.taskmanager.api.enums.ActiveStatus;
import lk.taskmanager.api.enums.UserRole;
import lk.taskmanager.api.model.User;
import lk.taskmanager.api.repository.UserRepository;
import lk.taskmanager.api.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

    @Override
    @Transactional
    public CommonResponse signup(UserSignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return CommonResponse.error("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(UserRole.ROLE_USER)
                .activeStatus(ActiveStatus.ACTIVE)
                .build();

        userRepository.save(user);
        log.info("New user registered: {}", request.getEmail());
        return CommonResponse.success("User registered successfully", toUserResponse(user));
    }

    @Override
    public UserResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
