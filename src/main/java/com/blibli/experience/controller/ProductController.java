package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.product.GetProductsAvailableCommand;
import com.blibli.experience.command.product.PostProductCommand;
import com.blibli.experience.model.request.product.PostProductRequest;
import com.blibli.experience.model.response.product.GetProductAvailableResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

  private CommandExecutor commandExecutor;

  @Autowired
  public ProductController(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @PostMapping(value = ApiPath.PRODUCT,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Response<String>> postProduct(@RequestBody PostProductRequest request) {
    return commandExecutor.execute(PostProductCommand.class, request)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @GetMapping(value = ApiPath.PRODUCT_AVAILABLE)
  public Mono<Response<List<GetProductAvailableResponse>>> getAvailableProduct(@RequestParam Integer count) {
    return commandExecutor.execute(GetProductsAvailableCommand.class, count)
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

}
