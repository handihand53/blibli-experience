package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.auth.LoginUserCommand;
import com.blibli.experience.command.auth.RegisterUserCommand;
import com.blibli.experience.model.request.auth.LoginUserRequest;
import com.blibli.experience.model.request.auth.RegisterUserRequest;
import com.blibli.experience.model.response.auth.LoginUserResponse;
import com.blibli.experience.model.response.auth.RegisterUserResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

  private CommandExecutor commandExecutor;

  @Autowired
  public AuthController(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @PostMapping(value = ApiPath.REGISTER, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<RegisterUserResponse>> registerUser(@RequestBody RegisterUserRequest request) {
    return commandExecutor.execute(RegisterUserCommand.class, request)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @PostMapping(value = ApiPath.LOGIN, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<LoginUserResponse>> loginUser(@RequestBody LoginUserRequest request) {
    return commandExecutor.execute(LoginUserCommand.class, request)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

}
