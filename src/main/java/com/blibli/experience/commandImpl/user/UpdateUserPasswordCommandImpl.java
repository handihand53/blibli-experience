package com.blibli.experience.commandImpl.user;

import com.blibli.experience.command.user.UpdateUserPasswordCommand;
import com.blibli.experience.entity.User;
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
    return userRepository.getFirstById(request.getId())
        .switchIfEmpty(Mono.error(new NotFoundException("User not found!")))
        .filter(user -> isPasswordMatch(user, request.getPassword()))
        .switchIfEmpty(Mono.error(new RuntimeException("Wrong password!")))
        .map(user -> {
          user.setPassword(passwordEncoder().encode(request.getNewPassword()));
          return user;
        })
        .flatMap(user -> userRepository.save(user)
            .thenReturn("User password updated successfully."));
  }

  private Boolean isPasswordMatch(User user, String password) {
    return passwordEncoder().matches(password, user.getPassword());
  }

}
