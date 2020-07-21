package com.blibli.experience.commandImpl.auth;

import com.blibli.experience.command.auth.RegisterAdminCommand;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.auth.RegisterAdminRequest;
import com.blibli.experience.model.response.auth.RegisterAdminResponse;
import com.blibli.experience.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RegisterAdminCommandImpl implements RegisterAdminCommand {

    private UserRepository userRepository;

    @Autowired
    public RegisterAdminCommandImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Mono<RegisterAdminResponse> execute(RegisterAdminRequest request) {
        return Mono.fromCallable(() -> toUser(request))
                .flatMap(user -> userRepository.save(user))
                .map(this::toResponse);
    }

    private User toUser(RegisterAdminRequest request) {
        User user = User.builder()
                .userId(UUID.randomUUID())
                .userCreatedAt(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(request, user);
        user.setUserPassword(passwordEncoder().encode(user.getUserPassword()));
        user.setUserRoles(getUserRole());
        return user;
    }

    private List<UserRole> getUserRole() {
        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_ADMIN);
        return roles;
    }

    private RegisterAdminResponse toResponse(User user) {
        RegisterAdminResponse response = new RegisterAdminResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

}
