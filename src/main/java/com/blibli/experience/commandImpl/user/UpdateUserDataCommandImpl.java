package com.blibli.experience.commandImpl.user;

import com.blibli.experience.command.user.UpdateUserDataCommand;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.model.request.user.UpdateUserDataRequest;
import com.blibli.experience.model.response.user.UpdateUserDataResponse;
import com.blibli.experience.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UpdateUserDataCommandImpl implements UpdateUserDataCommand {

    private UserRepository userRepository;

    @Autowired
    public UpdateUserDataCommandImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UpdateUserDataResponse> execute(UpdateUserDataRequest request) {
        return userRepository.findFirstByUserId(request.getUserId())
                .map(user -> updateUser(user, request))
                .flatMap(user -> userRepository.save(user))
                .map(this::toResponse);
    }

    private User updateUser(User user, UpdateUserDataRequest request) {
        BeanUtils.copyProperties(request, user);
        return user;
    }

    private UpdateUserDataResponse toResponse(User user) {
        UpdateUserDataResponse response = new UpdateUserDataResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }
}
