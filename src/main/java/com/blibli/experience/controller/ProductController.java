package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.product.*;
import com.blibli.experience.model.request.product.GetAllProductByCategoryRequest;
import com.blibli.experience.model.request.product.GetProductDetailWithBarcodeAndShopRequest;
import com.blibli.experience.model.response.product.*;
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

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private CommandExecutor commandExecutor;

    @Autowired
    public ProductController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.PRODUCT_WITH_BARCODE)
    public Mono<Response<GetProductDetailWithBarcodeAndShopResponse>> getDetailProductWithBarcodeAndShop(
            @ModelAttribute GetProductDetailWithBarcodeAndShopRequest request) {
        return commandExecutor.execute(GetProductDetailWithBarcodeAndShopCommand.class, request)
                .log("#getDetailProductWithBarcode - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_CATEGORY_ENUM)
    public Mono<Response<GetProductCategoryEnumResponse>> getProductCategoryEnum() {
        return commandExecutor.execute(GetProductCategoryEnumCommand.class, "")
                .log("#getProductCategoryEnum - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_AVAILABLE)
    public Mono<Response<List<GetAllProductAvailableResponse>>> getAllProductAvailable(@RequestParam Integer skipCount) {
        return commandExecutor.execute(GetAllProductAvailableCommand.class, skipCount)
                .log("#getAllProductAvailable - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_CATEGORY)
    public Mono<Response<List<GetAllProductByCategoryResponse>>> getAllProductCategory(@ModelAttribute GetAllProductByCategoryRequest request) {
        return commandExecutor.execute(GetAllProductByCategoryCommand.class, request)
                .log("#getAllProductCategory - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_SEARCH_AVAILABLE)
    public Mono<Response<List<GetAllProductWithNameAndAvailableStatusResponse>>> getAllProductWithSearchKeyToStock(
            @RequestParam String searchKey) {
        return commandExecutor.execute(GetAllProductWithNameAndAvailableStatusCommand.class, searchKey)
                .log("#getAllProductWithSearchKeyToStock - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
