package com.blibli.experience.commandImpl.auth;

import com.blibli.experience.command.auth.RegisterUserCommand;
import com.blibli.experience.entity.User;
import com.blibli.experience.model.request.auth.RegisterUserRequest;
import com.blibli.experience.model.response.auth.RegisterUserResponse;
import com.blibli.experience.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegisterUserCommandImpl implements RegisterUserCommand {

  private UserRepository userRepository;

  @Autowired
  public RegisterUserCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  };

  @Override
  public Mono<RegisterUserResponse> execute(RegisterUserRequest request) {
    return Mono.fromCallable(() -> registerUser(request))
        .flatMap(user -> userRepository.save(user))
        .map(this::toResponse);
  }

  private User registerUser(RegisterUserRequest request) {
    User user = User.builder()
        .id(UUID.randomUUID())
        .createdAt(LocalDateTime.now().toString())
        .build();
    BeanUtils.copyProperties(request, user);
    user.setPassword(passwordEncoder().encode(user.getPassword()));
    return user;
  }

  private RegisterUserResponse toResponse(User user) {
    RegisterUserResponse response = new RegisterUserResponse();
    BeanUtils.copyProperties(user, response);
    return response;
  }

}
