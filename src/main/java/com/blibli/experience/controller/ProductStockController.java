package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productStock.GetAllProductStockInShopCommand;
import com.blibli.experience.command.productStock.PostProductStockCommand;
import com.blibli.experience.model.request.productStock.GetAllProductStockInShopRequest;
import com.blibli.experience.model.request.productStock.PostProductStockRequest;
import com.blibli.experience.model.response.productStock.GetAllProductStockInShopResponse;
import com.blibli.experience.model.response.productStock.PostProductStockResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductStockController {

    private CommandExecutor commandExecutor;

    @Autowired
    public ProductStockController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @PostMapping(value = ApiPath.MERCHANT_PRODUCT_STOCK, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostProductStockResponse>> postProductStock(@RequestBody PostProductStockRequest request) {
        return commandExecutor.execute(PostProductStockCommand.class, request)
                .log("#postProductStock - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.MERCHANT_PRODUCT_STOCK, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<Flux<GetAllProductStockInShopResponse>>> getAllProductStockInShop(@RequestBody GetAllProductStockInShopRequest request) {
        return commandExecutor.execute(GetAllProductStockInShopCommand.class, request)
                .log("#getAllProductStockInShop - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}