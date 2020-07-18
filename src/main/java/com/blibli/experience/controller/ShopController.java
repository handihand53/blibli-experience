package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.shop.GetAllShopLocationCommand;
import com.blibli.experience.command.shop.GetShopDetailCommand;
import com.blibli.experience.model.response.shop.GetAllShopLocationResponse;
import com.blibli.experience.model.response.shop.GetShopDetailResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {

    private CommandExecutor commandExecutor;

    @Autowired
    public ShopController(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    @GetMapping(value = ApiPath.SHOP)
    public Mono<Response<GetShopDetailResponse>> getShopDetailByUserId(@RequestParam UUID userId) {
        return commandExecutor.execute(GetShopDetailCommand.class, userId)
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.PRODUCT_BLIBLIMART)
    public Mono<Response<List<GetAllShopLocationResponse>>> getBliblimartLocation() {
        return commandExecutor.execute(GetAllShopLocationCommand.class, 100)
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
