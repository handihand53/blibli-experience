package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productBarter.GetProductBarterAvailableCommand;
import com.blibli.experience.command.productBarter.PostProductBarterCommand;
import com.blibli.experience.model.request.productBarter.PostProductBarterRequest;
import com.blibli.experience.model.response.productBarter.GetProductBarterAvailableResponse;
import com.blibli.experience.model.response.productBarter.PostProductBarterResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductBarterController {

    private CommandExecutor commandExecutor;
    private ObjectMapper objectMapper;

    @Autowired
    public ProductBarterController(CommandExecutor commandExecutor, ObjectMapper objectMapper) {
        this.commandExecutor = commandExecutor;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = ApiPath.BARTER_AVAILABLE)
    public Mono<Response<List<GetProductBarterAvailableResponse>>> getProductBarterAvailable(
            @RequestParam Integer skipCount) {
        return commandExecutor.execute(GetProductBarterAvailableCommand.class, skipCount)
                .log("#getProductBarterAvailable - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.BARTER)
    public Mono<Response<PostProductBarterResponse>> postProductBarter(
            @RequestParam List<MultipartFile> images, @RequestParam String productBarterMetaData) throws IOException {
        PostProductBarterRequest request = objectMapper.readValue(productBarterMetaData, PostProductBarterRequest.class);
        request.setProductBarterImages(images);
        return commandExecutor.execute(PostProductBarterCommand.class, request)
                .log("#postProductBarter - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
