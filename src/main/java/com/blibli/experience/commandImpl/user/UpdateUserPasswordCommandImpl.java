package com.blibli.experience.commandImpl.user;

import com.blibli.experience.command.user.UpdateUserPasswordCommand;
import com.blibli.experience.entity.User;
import com.blibli.experience.model.request.user.UpdateUserPasswordRequest;
import com.blibli.experience.repository.UserRepository;
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
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  };

  @Override
  public Mono<String> execute(UpdateUserPasswordRequest request) {
    return userRepository.getFirstById(request.getId())
        .map(user -> {
          if(isPasswordMatch(user, request.getPassword())) {
            user.setPassword(request.getNewPassword());
          }
          return user; })
        .flatMap(user -> userRepository.save(user)
          .thenReturn("User password updated successfully."));
  }

  private Boolean isPasswordMatch(User user, String password) {
    return passwordEncoder().matches(password, user.getPassword());
  }

}
