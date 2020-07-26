package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productBidding.GetAllProductBiddingAvailableCommand;
import com.blibli.experience.command.productBidding.GetProductBiddingDetailCommand;
import com.blibli.experience.command.productBidding.PostProductBiddingCommand;
import com.blibli.experience.model.request.productBidding.PostProductBiddingRequest;
import com.blibli.experience.model.response.productBidding.GetAllProductBiddingAvailableResponse;
import com.blibli.experience.model.response.productBidding.GetProductBiddingDetailResponse;
import com.blibli.experience.model.response.productBidding.PostProductBiddingResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
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
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductBiddingController {

    private CommandExecutor commandExecutor;
    private ObjectMapper objectMapper;

    @Autowired
    public ProductBiddingController(CommandExecutor commandExecutor, ObjectMapper objectMapper) {
        this.commandExecutor = commandExecutor;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = ApiPath.PRODUCT_BIDDING)
    public Mono<Response<GetProductBiddingDetailResponse>> getProductBiddingDetail(
            @RequestParam UUID productBiddingId) {
        return commandExecutor.execute(GetProductBiddingDetailCommand.class, productBiddingId)
                .log("#getProductBiddingDetail - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_BIDDING_AVAILABLE)
    public Mono<Response<List<GetAllProductBiddingAvailableResponse>>> getProductBiddingAvailable(
            @RequestParam Integer skipCount) {
        return commandExecutor.execute(GetAllProductBiddingAvailableCommand.class, skipCount)
                .log("#getProductBiddingAvailable - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.PRODUCT_BIDDING)
    public Mono<Response<PostProductBiddingResponse>> postProductBidding(
            @RequestParam List<MultipartFile> images, @RequestParam String productBiddingMetaData) throws IOException {
        PostProductBiddingRequest request = objectMapper.readValue(productBiddingMetaData, PostProductBiddingRequest.class);
        request.setProductImages(images);
        return commandExecutor.execute(PostProductBiddingCommand.class, request)
                .log("#postProductBidding - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }
}
