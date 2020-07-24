package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.barterOrder.*;
import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.model.request.barterOrder.*;
import com.blibli.experience.model.response.barterOrder.*;
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
public class BarterOrderController {

    private CommandExecutor commandExecutor;

    @Autowired
    public BarterOrderController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.BARTER_ORDER)
    public Mono<Response<GetBarterOrderDetailResponse>> getBarterOrderDetail(
            @RequestParam UUID barterOrderId) {
        return commandExecutor.execute(GetBarterOrderDetailCommand.class, barterOrderId)
                .log("#getBarterOrderDetail - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.BARTER_ORDER_BY_USER)
    public Mono<Response<List<GetAllBarterOrderByUserIdResponse>>> getAllBarterOrderByUserId(
            @ModelAttribute GetAllBarterOrderByUserIdRequest request) {
        return commandExecutor.execute(GetAllBarterOrderByUserIdCommand.class, request)
                .log("#getAllBarterOrderByUserId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.ADMIN_BARTER_ORDER_BY_ITEM_STATUS)
    public Mono<Response<List<GetAllBarterOrderByItemStatusResponse>>> getAllBarterOrderByItemStatus(
            @ModelAttribute GetAllBarterOrderByItemStatusRequest request) {
        return commandExecutor.execute(GetAllBarterOrderByItemStatusCommand.class, request)
                .log("#getAllBarterOrderByItemStatus - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.ADMIN_BARTER_ORDER_BY_ORDER_STATUS)
    public Mono<Response<List<GetAllBarterOrderByOrderStatusResponse>>> getAllBarterOrderByOrderStatus(
            @RequestParam BarterOrderStatus request) {
        return commandExecutor.execute(GetAllBarterOrderByOrderStatusCommand.class, request)
                .log("#getAllBarterOrderByOrderStatus - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.BARTER_ORDER, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostBarterOrderResponse>> postBarterOrder(
            @RequestBody PostBarterOrderRequest request) {
        return commandExecutor.execute(PostBarterOrderCommand.class, request)
                .log("#postBarterOrder - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.BARTER_ORDER_RECEIPT_TO_WAREHOUSE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateBarterOrderReceiptToWarehouseResponse>> updateBarterOrderReceiptToWarehouse(
            @RequestBody UpdateBarterOrderReceiptToWarehouseRequest request) {
        return commandExecutor.execute(UpdateBarterOrderReceiptToWarehouseCommand.class, request)
                .log("#updateBarterOrderReceiptToWarehouse - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.ADMIN_BARTER_ORDER_RECEIPT_TO_VERIFIED, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateBarterOrderReceiptToVerifiedResponse>> updateBarterOrderReceiptToVerified(
            @RequestBody UpdateBarterOrderReceiptToVerifiedRequest request) {
        return commandExecutor.execute(UpdateBarterOrderReceiptToVerifiedCommand.class, request)
                .log("#updateBarterOrderReceiptToVerified - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.ADMIN_BARTER_ORDER_RECEIPT_TO_CONSUMERS, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateBarterOrderReceiptToConsumersResponse>> updateBarterOrderReceiptToConsumers(
            @RequestBody UpdateBarterOrderReceiptToConsumersRequest request) {
        return commandExecutor.execute(UpdateBarterOrderReceiptToConsumersCommand.class, request)
                .log("#updateBarterOrderReceiptToConsumers - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.BARTER_ORDER_RECEIPT_IN_CONSUMERS, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateBarterOrderItemInConsumersResponse>> updateBarterOrderReceiptInConsumers(
            @RequestBody UpdateBarterOrderItemInConsumersRequest request) {
        return commandExecutor.execute(UpdateBarterOrderItemInConsumersCommand.class, request)
                .log("#updateBarterOrderReceiptInConsumers - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
