package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.user.GetUserDetailCommand;
import com.blibli.experience.command.user.UpdateUserPasswordCommand;
import com.blibli.experience.model.request.user.UpdateUserPasswordRequest;
import com.blibli.experience.model.response.user.GetUserDetailResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  private CommandExecutor commandExecutor;

  @Autowired
  public UserController(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @GetMapping(value = ApiPath.USER)
  public Mono<Response<GetUserDetailResponse>> getUserData(@RequestParam UUID id) {
    return commandExecutor.execute(GetUserDetailCommand.class, id)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @PutMapping(value = ApiPath.USER_UPDATE_PASSWORD)
  public Mono<Response<String>> updateUserPassword(@RequestBody UpdateUserPasswordRequest request) {
    return commandExecutor.execute(UpdateUserPasswordCommand.class, request)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

}
