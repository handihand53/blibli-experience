package com.blibli.experience.commandImpl.auth;

import com.blibli.experience.command.auth.RegisterUserCommand;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.entity.form.AddressForm;
import com.blibli.experience.model.request.auth.RegisterUserRequest;
import com.blibli.experience.model.response.auth.RegisterUserResponse;
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
public class RegisterUserCommandImpl implements RegisterUserCommand {

  private UserRepository userRepository;

  @Autowired
  public RegisterUserCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public Mono<RegisterUserResponse> execute(RegisterUserRequest request) {
    return Mono.fromCallable(() -> toUser(request))
        .flatMap(user -> userRepository.save(user))
        .map(this::toResponse);
  }

  private User toUser(RegisterUserRequest request) {
    User user = User.builder()
        .id(UUID.randomUUID())
        .createdAt(LocalDateTime.now())
        .build();
    BeanUtils.copyProperties(request, user);
    user.setPassword(passwordEncoder().encode(user.getPassword()));
    user.setRoles(getRoles());
    user.setAddressForms(getAddress());
    return user;
  }

  private List<AddressForm> getAddress() {
    List<AddressForm> addressForms = new ArrayList<>();
    addressForms.add(AddressForm.builder()
        .province("DKI Jakarta")
        .build());
    return addressForms;
  }

  private List<UserRole> getRoles() {
    List<UserRole> roles = new ArrayList<>();
    roles.add(UserRole.USER);
    return roles;
  }

  private RegisterUserResponse toResponse(User user) {
    RegisterUserResponse response = new RegisterUserResponse();
    BeanUtils.copyProperties(user, response);
    return response;
  }

}
