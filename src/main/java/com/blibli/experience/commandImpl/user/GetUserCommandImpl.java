package com.blibli.experience.commandImpl.user;

import com.blibli.experience.command.user.GetUserCommand;
import com.blibli.experience.entity.User;
import com.blibli.experience.model.response.user.GetUserResponse;
import com.blibli.experience.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class GetUserCommandImpl implements GetUserCommand {

  private UserRepository userRepository;

  @Autowired
  public GetUserCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Mono<GetUserResponse> execute(UUID id) {
    return userRepository.findFirstById(id)
        .switchIfEmpty(Mono.error(new NotFoundException("User not found!")))
        .map(this::toResponse);
  }

  private GetUserResponse toResponse(User user) {
    GetUserResponse response = new GetUserResponse();
    BeanUtils.copyProperties(user, response);
    return response;
  }

}
