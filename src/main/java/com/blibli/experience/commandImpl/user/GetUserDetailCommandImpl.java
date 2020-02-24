package com.blibli.experience.commandImpl.user;

import com.blibli.experience.command.user.GetUserDetailCommand;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.model.response.user.GetUserDetailResponse;
import com.blibli.experience.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class GetUserDetailCommandImpl implements GetUserDetailCommand {

  private UserRepository userRepository;

  @Autowired
  public GetUserDetailCommandImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Mono<GetUserDetailResponse> execute(UUID id) {
    return userRepository.findFirstByUserId(id)
        .switchIfEmpty(Mono.error(new NotFoundException("User not found!")))
        .map(this::toResponse);
  }

  private GetUserDetailResponse toResponse(User user) {
    GetUserDetailResponse response = new GetUserDetailResponse();
    BeanUtils.copyProperties(user, response);
    return response;
  }

}
