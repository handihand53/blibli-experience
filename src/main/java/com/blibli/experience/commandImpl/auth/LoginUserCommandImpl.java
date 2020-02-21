package com.blibli.experience.commandImpl.auth;

import com.blibli.experience.command.auth.LoginUserCommand;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.model.request.auth.LoginUserRequest;
import com.blibli.experience.model.response.auth.LoginUserResponse;
import com.blibli.experience.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LoginUserCommandImpl implements LoginUserCommand {

  private UserRepository userRepository;

  @Autowired
  public LoginUserCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public Mono<LoginUserResponse> execute(LoginUserRequest request) {
    return userRepository.findFirstByEmail(request.getEmail())
        .switchIfEmpty(Mono.error(new NotFoundException("User not found!")))
        .filter(user -> isPasswordMatch(user, request.getPassword()))
        .switchIfEmpty(Mono.error(new RuntimeException("Wrong password!")))
        .map(this::toResponse);
  }

  private Boolean isPasswordMatch(User user, String password) {
    return passwordEncoder().matches(password, user.getPassword());
  }

  private LoginUserResponse toResponse(User user) {
    LoginUserResponse response = new LoginUserResponse();
    BeanUtils.copyProperties(user, response);
    response.setAccessToken("Beta Token");
    return response;
  }

}
