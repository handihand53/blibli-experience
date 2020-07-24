package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.barterOrder.PostBarterOrderCommand;
import com.blibli.experience.command.payment.PostPaymentCommand;
import com.blibli.experience.model.request.barterOrder.PostBarterOrderRequest;
import com.blibli.experience.model.request.payment.PostPaymentRequest;
import com.blibli.experience.model.response.barterOrder.PostBarterOrderResponse;
import com.blibli.experience.model.response.payment.PostPaymentResponse;
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
public class PaymentController {

    private CommandExecutor commandExecutor;

    @Autowired
    public PaymentController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @PostMapping(value = ApiPath.PAYMENT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<PostPaymentResponse>> postPayment(
            @RequestBody PostPaymentRequest request) {
        return commandExecutor.execute(PostPaymentCommand.class, request)
                .log("#postPayment - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
