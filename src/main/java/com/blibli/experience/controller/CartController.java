package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.cart.GetCartWithUserIdCommand;
import com.blibli.experience.model.response.cart.GetCartWithUserIdResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class CartController {

  private CommandExecutor commandExecutor;

  @Autowired
  public CartController(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @GetMapping(value = ApiPath.CARTS)
  public Mono<Response<GetCartWithUserIdResponse>> getCartWithUserId(@RequestParam UUID id) {
    return commandExecutor.execute(GetCartWithUserIdCommand.class, id)
        .log("#getCartWithUserId - Successfully executing command.")
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

}
