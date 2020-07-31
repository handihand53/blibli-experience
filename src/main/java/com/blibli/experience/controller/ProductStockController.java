package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productStock.GetAllProductStockInShopCommand;
import com.blibli.experience.command.productStock.PostProductStockCommand;
import com.blibli.experience.command.productStock.SynchronizeAllProductDataFormCommand;
import com.blibli.experience.command.productStock.UpdateProductStockCommand;
import com.blibli.experience.model.request.productStock.PostProductStockRequest;
import com.blibli.experience.model.request.productStock.UpdateProductStockRequest;
import com.blibli.experience.model.response.productStock.GetAllProductStockInShopResponse;
import com.blibli.experience.model.response.productStock.PostProductStockResponse;
import com.blibli.experience.model.response.productStock.UpdateProductStockResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

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

    @PostMapping(value = ApiPath.ADMIN_SYNCHRONIZE_PRODUCT_DATA_FORM_IN_STOCK)
    public Mono<Response<String>> synchronizeProductDataForm() {
        return commandExecutor.execute(SynchronizeAllProductDataFormCommand.class, 1)
                .log("#synchronizeProductDataForm - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.MERCHANT_PRODUCT_STOCK)
    public Mono<Response<List<GetAllProductStockInShopResponse>>> getAllProductStockInShop(@RequestParam UUID shopId) {
        return commandExecutor.execute(GetAllProductStockInShopCommand.class, shopId)
                .log("#getAllProductStockInShop - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.MERCHANT_PRODUCT_STOCK, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateProductStockResponse>> updateProductStock(@RequestBody UpdateProductStockRequest request) {
        return commandExecutor.execute(UpdateProductStockCommand.class, request)
                .log("#updateProductStock - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}