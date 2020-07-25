package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.cart.DeleteProductInCartCommand;
import com.blibli.experience.command.cart.GetCartWithUserIdCommand;
import com.blibli.experience.command.cart.PostProductToCartCommand;
import com.blibli.experience.command.cart.UpdateCartProductAmountCommand;
import com.blibli.experience.model.request.cart.DeleteProductInCartRequest;
import com.blibli.experience.model.request.cart.PostProductToCartRequest;
import com.blibli.experience.model.request.cart.UpdateCartProductAmountRequest;
import com.blibli.experience.model.response.cart.DeleteProductInCartResponse;
import com.blibli.experience.model.response.cart.GetCartWithUserIdResponse;
import com.blibli.experience.model.response.cart.PostProductToCartResponse;
import com.blibli.experience.model.response.cart.UpdateCartProductAmountResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController {

  private CommandExecutor commandExecutor;

  @Autowired
  public CartController(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @GetMapping(value = ApiPath.CARTS)
  public Mono<Response<GetCartWithUserIdResponse>> getCartWithUserId(@RequestParam UUID userId) {
    return commandExecutor.execute(GetCartWithUserIdCommand.class, userId)
            .log("#getCartWithUserId - Successfully executing command.")
            .map(ResponseHelper::ok)
            .subscribeOn(Schedulers.elastic());
  }

  @PostMapping(value = ApiPath.CARTS, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<PostProductToCartResponse>> postProductToCart(@RequestBody PostProductToCartRequest request) {
    return commandExecutor.execute(PostProductToCartCommand.class, request)
        .log("#postProductToCart - Successfully executing command.")
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @PutMapping(value = ApiPath.CARTS_UPDATE_AMOUNT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<UpdateCartProductAmountResponse>> updateCartProductAmount(@RequestBody UpdateCartProductAmountRequest request) {
    return commandExecutor.execute(UpdateCartProductAmountCommand.class, request)
            .log("#updateCartProductAmount - Successfully executing command.")
            .map(ResponseHelper::ok)
            .subscribeOn(Schedulers.elastic());
  }

  @PutMapping(value = ApiPath.CARTS_DELETE_PRODUCT, consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<DeleteProductInCartResponse>> deleteProductInCart(@RequestBody DeleteProductInCartRequest request) {
    return commandExecutor.execute(DeleteProductInCartCommand.class, request)
            .log("#deleteProductInCart - Successfully executing command.")
            .map(ResponseHelper::ok)
            .subscribeOn(Schedulers.elastic());
  }

}
