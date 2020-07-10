package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productMaster.PostProductMasterCommand;
import com.blibli.experience.command.productStock.PostProductStockCommand;
import com.blibli.experience.model.request.productMaster.PostProductMasterRequest;
import com.blibli.experience.model.request.productStock.PostProductStockRequest;
import com.blibli.experience.model.response.productMaster.PostProductMasterResponse;
import com.blibli.experience.model.response.productStock.PostProductStockResponse;
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
public class ProductStockController {

    private CommandExecutor commandExecutor;

    @Autowired
    public ProductStockController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @PostMapping(value = ApiPath.ADD_PRODUCT_STOCK, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostProductStockResponse>> postProductStock(@RequestBody PostProductStockRequest request) {
        return commandExecutor.execute(PostProductStockCommand.class, request)
                .log("#postProductStock - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}