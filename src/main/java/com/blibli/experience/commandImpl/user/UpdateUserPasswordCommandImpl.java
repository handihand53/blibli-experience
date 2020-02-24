package com.blibli.experience.commandImpl.user;

import com.blibli.experience.command.user.UpdateUserPasswordCommand;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.model.request.user.UpdateUserPasswordRequest;
import com.blibli.experience.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateUserPasswordCommandImpl implements UpdateUserPasswordCommand {

  private UserRepository userRepository;

  @Autowired
  public UpdateUserPasswordCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public Mono<String> execute(UpdateUserPasswordRequest request) {
    return userRepository.findFirstByUserId(request.getUserId())
        .switchIfEmpty(Mono.error(new NotFoundException("User not found!")))
        .filter(user -> isPasswordMatch(user, request.getUserPassword()))
        .switchIfEmpty(Mono.error(new RuntimeException("Wrong password!")))
        .map(user -> {
          user.setUserPassword(passwordEncoder().encode(request.getUserNewPassword()));
          return user;
        })
        .flatMap(user -> userRepository.save(user)
            .thenReturn("Success!"));
  }

  private Boolean isPasswordMatch(User user, String password) {
    return passwordEncoder().matches(password, user.getUserPassword());
  }

}
