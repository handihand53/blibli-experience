package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productBarter.GetProductBarterAvailableCommand;
import com.blibli.experience.command.productBarter.GetProductBarterDetailCommand;
import com.blibli.experience.command.productBarter.PostProductBarterCommand;
import com.blibli.experience.model.request.productBarter.PostProductBarterRequest;
import com.blibli.experience.model.response.productBarter.GetProductBarterAvailableResponse;
import com.blibli.experience.model.response.productBarter.GetProductBarterDetailResponse;
import com.blibli.experience.model.response.productBarter.PostProductBarterResponse;
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
public class ProductBarterController {

    private CommandExecutor commandExecutor;

    @Autowired
    public ProductBarterController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.BARTER_AVAILABLE)
    public Mono<Response<List<GetProductBarterAvailableResponse>>> getProductBarterAvailable(
            @RequestParam Integer skipCount) {
        return commandExecutor.execute(GetProductBarterAvailableCommand.class, skipCount)
                .log("#getProductBarterAvailable - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.BARTER)
    public Mono<Response<GetProductBarterDetailResponse>> getProductBarterDetail(
            @RequestParam UUID productBarterId) {
        return commandExecutor.execute(GetProductBarterDetailCommand.class, productBarterId)
                .log("#getProductBarterDetail - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.BARTER, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostProductBarterResponse>> postProductBarter(
            @RequestBody PostProductBarterRequest request) {
        return commandExecutor.execute(PostProductBarterCommand.class, request)
                .log("#postProductBarter - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
