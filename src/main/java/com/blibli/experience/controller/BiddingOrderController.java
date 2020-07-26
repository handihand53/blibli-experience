package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.barterOrder.GetAllBarterOrderByUserIdCommand;
import com.blibli.experience.command.barterOrder.GetBarterOrderDetailCommand;
import com.blibli.experience.command.barterOrder.UpdateBarterOrderReceiptToWarehouseCommand;
import com.blibli.experience.command.biddingOrder.*;
import com.blibli.experience.command.biddingPayment.PostBiddingPaymentCommand;
import com.blibli.experience.model.request.barterOrder.GetAllBarterOrderByUserIdRequest;
import com.blibli.experience.model.request.barterOrder.UpdateBarterOrderReceiptToWarehouseRequest;
import com.blibli.experience.model.request.biddingOrder.PostBiddingOrderRequest;
import com.blibli.experience.model.request.biddingOrder.UpdateBiddingOrderDeliveryReceiptRequest;
import com.blibli.experience.model.request.biddingPayment.PostBiddingPaymentRequest;
import com.blibli.experience.model.response.barterOrder.GetAllBarterOrderByUserIdResponse;
import com.blibli.experience.model.response.barterOrder.GetBarterOrderDetailResponse;
import com.blibli.experience.model.response.barterOrder.UpdateBarterOrderReceiptToWarehouseResponse;
import com.blibli.experience.model.response.biddingOrder.*;
import com.blibli.experience.model.response.biddingPayment.PostBiddingPaymentResponse;
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
public class BiddingOrderController {

    private CommandExecutor commandExecutor;

    @Autowired
    public BiddingOrderController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.BIDDING_ORDER)
    public Mono<Response<GetBiddingOrderDetailResponse>> getBiddingOrderDetail(
            @RequestParam UUID biddingOrderId) {
        return commandExecutor.execute(GetBiddingOrderDetailCommand.class, biddingOrderId)
                .log("#getBiddingOrderDetail - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.BIDDING_ORDER_BY_OWNER)
    public Mono<Response<List<GetAllBiddingOrderByOwnerUserIdResponse>>> getAllBiddingOrderByOwnerUserId(
            @RequestParam UUID userId) {
        return commandExecutor.execute(GetAllBiddingOrderByOwnerUserIdCommand.class, userId)
                .log("#getAllBiddingOrderByOwnerUserId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.BIDDING_ORDER_BY_WINNER)
    public Mono<Response<List<GetAllBiddingOrderByWinnerUserIdResponse>>> getAllBiddingOrderByWinnerUserId(
            @RequestParam UUID userId) {
        return commandExecutor.execute(GetAllBiddingOrderByWinnerUserIdCommand.class, userId)
                .log("#getAllBiddingOrderByWinnerUserId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.BIDDING_ORDER, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostBiddingOrderResponse>> postBiddingOrder(
            @RequestBody PostBiddingOrderRequest request) {
        return commandExecutor.execute(PostBiddingOrderCommand.class, request)
                .log("#postBiddingOrder - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.BIDDING_ORDER_DELIVERY_RECEIPT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<UpdateBiddingOrderDeliveryReceiptResponse>> updateBiddingOrderDeliveryReceipt(
            @RequestBody UpdateBiddingOrderDeliveryReceiptRequest request) {
        return commandExecutor.execute(UpdateBiddingOrderDeliveryReceiptCommand.class, request)
                .log("#updateBiddingOrderDeliveryReceipt - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PutMapping(value = ApiPath.BIDDING_ORDER_CONFIRMATION)
    public Mono<Response<UpdateBiddingOrderToConfirmationResponse>> updateBiddingOrderToConfirmation(
            @RequestParam UUID biddingOrderId) {
        return commandExecutor.execute(UpdateBiddingOrderToConfirmationCommand.class, biddingOrderId)
                .log("#updateBiddingOrderToConfirmation - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
