package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productBidding.*;
import com.blibli.experience.model.request.productBidding.GetAllProductBiddingByAvailableAndCategoryRequest;
import com.blibli.experience.model.request.productBidding.PostProductBiddingRequest;
import com.blibli.experience.model.request.productBidding.UpdateProductBiddingToBidRequest;
import com.blibli.experience.model.response.productBidding.*;
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

    @GetMapping(value = ApiPath.METADATA_PRODUCT_BIDDING)
    public Mono<Response<GetProductBiddingMetadataResponse>> getProductBiddingMetadata() {
        return commandExecutor.execute(GetProductBiddingMetadataCommand.class, 1)
                .log("#getProductBiddingMetadata - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
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

    @GetMapping(value = ApiPath.PRODUCT_BIDDING_BY_CATEGORY)
    public Mono<Response<List<GetAllProductBiddingByAvailableAndCategoryResponse>>> getProductBiddingByCategory(
            @ModelAttribute GetAllProductBiddingByAvailableAndCategoryRequest request) {
        return commandExecutor.execute(GetAllProductBiddingByAvailableAndCategoryCommand.class, request)
                .log("#getProductBiddingAvailable - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_BIDDING_BY_USER)
    public Mono<Response<List<GetAllProductBiddingByUserAndAvailableResponse>>> getProductBiddingByUserAndAvailable(
            @RequestParam UUID userId) {
        return commandExecutor.execute(GetAllProductBiddingByUserAndAvailableCommand.class, userId)
                .log("#getProductBiddingByUserAndAvailable - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_BIDDING_BY_USER_FINISHED)
    public Mono<Response<List<GetAllProductBiddingFinishedByUserIdResponse>>> getAllProductBiddingFinishedByUserId(
            @RequestParam UUID userId) {
        return commandExecutor.execute(GetAllProductBiddingFinishedByUserIdCommand.class, userId)
                .log("#getAllProductBiddingFinishedByUserId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_BIDDING_BIDDING_FORM_BY_USER)
    public Mono<Response<List<GetAllProductBiddingBidByUserResponse>>> getAllProductBiddingBidByUser(
            @RequestParam UUID userId) {
        return commandExecutor.execute(GetAllProductBiddingBidByUserCommand.class, userId)
                .log("#getAllProductBiddingBidByUser - Successfully executing command.")
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

    @PutMapping(value = ApiPath.PRODUCT_BIDDING_TO_BID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateProductBiddingToBidResponse>> updateProductBiddingToBid(
            @RequestBody UpdateProductBiddingToBidRequest request) {
        return commandExecutor.execute(UpdateProductBiddingToBidCommand.class, request)
                .log("#updateProductBiddingToBid - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.ADMIN_PRODUCT_BIDDING_TO_CLOSE)
    public Mono<Response<List<UpdateProductBiddingToCloseResponse>>> updateProductBiddingToClose() {
        return commandExecutor.execute(UpdateProductBiddingToCloseCommand.class, 1)
                .log("#updateProductBiddingToClose - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
