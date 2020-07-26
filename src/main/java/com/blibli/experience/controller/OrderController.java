package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.order.*;
import com.blibli.experience.model.request.order.PostOrderRequest;
import com.blibli.experience.model.request.order.UpdateOrderDeliveryReceiptRequest;
import com.blibli.experience.model.response.order.*;
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
public class OrderController {

    private CommandExecutor commandExecutor;

    @Autowired
    public OrderController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.ORDER)
    public Mono<Response<GetOrderDetailResponse>> getOrderDetail(
            @RequestParam UUID orderId) {
        return commandExecutor.execute(GetOrderDetailCommand.class, orderId)
                .log("#getOrderDetail - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.ORDER_BY_USER)
    public Mono<Response<List<GetAllOrderByUserIdResponse>>> getAllOrderByUserId(
            @RequestParam UUID userId) {
        return commandExecutor.execute(GetAllOrderByUserIdCommand.class, userId)
                .log("#getAllOrderByUserId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.MERCHANT_ORDER)
    public Mono<Response<List<GetAllOrderByShopIdResponse>>> getAllOrderByShopId(
            @RequestParam UUID shopId) {
        return commandExecutor.execute(GetAllOrderByShopIdCommand.class, shopId)
                .log("#getAllOrderByShopId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.ORDER, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostOrderResponse>> postOrder(
            @RequestBody PostOrderRequest request) {
        return commandExecutor.execute(PostOrderCommand.class, request)
                .log("#postOrder - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.ORDER_TO_FINISHED)
    public Mono<Response<UpdateOrderStatusToFinishedResponse>> updateOrderToFinished(
            @RequestParam UUID orderId) {
        return commandExecutor.execute(UpdateOrderStatusToFinishedCommand.class, orderId)
                .log("#updateOrderToFinished - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.MERCHANT_ORDER_DELIVERY_RECEIPT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateOrderDeliveryReceiptResponse>> updateOrderDeliveryReceipt(
            @RequestBody UpdateOrderDeliveryReceiptRequest request) {
        return commandExecutor.execute(UpdateOrderDeliveryReceiptCommand.class, request)
                .log("#updateOrderDeliveryReceipt - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }


}
