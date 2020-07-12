package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productMaster.GetAllProductMasterCommand;
import com.blibli.experience.command.productMaster.GetAllProductMasterWithNameContainingCommand;
import com.blibli.experience.command.productMaster.GetProductMasterDetailWithIdCommand;
import com.blibli.experience.command.productMaster.PostProductMasterCommand;
import com.blibli.experience.model.request.productMaster.PostProductMasterRequest;
import com.blibli.experience.model.response.product.GetProductDetailWithIdAndShopIdResponse;
import com.blibli.experience.model.response.productMaster.GetAllProductMasterResponse;
import com.blibli.experience.model.response.productMaster.GetAllProductMasterWithNameContainingResponse;
import com.blibli.experience.model.response.productMaster.PostProductMasterResponse;
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
public class ProductMasterController {

    private CommandExecutor commandExecutor;

    @Autowired
    public ProductMasterController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @PostMapping(value = ApiPath.ADMIN_PRODUCT_MASTER, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostProductMasterResponse>> postProductMaster(@RequestBody PostProductMasterRequest request) {
        return commandExecutor.execute(PostProductMasterCommand.class, request)
                .log("#postProductMaster - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT)
    public Mono<Response<GetProductDetailWithIdAndShopIdResponse>> getDetailProductWithId(@RequestParam UUID id) {
        return commandExecutor.execute(GetProductMasterDetailWithIdCommand.class, id)
                .log("#getDetailProductWithId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCTS_ALL)
    public Mono<Response<List<GetAllProductMasterResponse>>> getAllProductMaster(@RequestParam Integer count) {
        return commandExecutor.execute(GetAllProductMasterCommand.class, count)
                .log("#getAllProductMaster - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_SEARCH)
    public Mono<Response<List<GetAllProductMasterWithNameContainingResponse>>> getProductWithSearchKey(@RequestParam String searchKey) {
        return commandExecutor.execute(GetAllProductMasterWithNameContainingCommand.class, searchKey)
                .log("#getDetailProductWithId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
