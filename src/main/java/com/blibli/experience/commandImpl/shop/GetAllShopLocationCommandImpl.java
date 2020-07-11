package com.blibli.experience.commandImpl.shop;

import com.blibli.experience.command.shop.GetAllShopLocationCommand;
import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.model.response.shop.GetAllShopLocationResponse;
import com.blibli.experience.repository.ShopRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllShopLocationCommandImpl implements GetAllShopLocationCommand {

    private ShopRepository shopRepository;

    @Autowired
    public GetAllShopLocationCommandImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Mono<List<GetAllShopLocationResponse>> execute(Integer request) {
        return shopRepository.findAll()
                .switchIfEmpty(Mono.error(new NotFoundException("Shop not found!")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllShopLocationResponse toResponse(Shop shop) {
        GetAllShopLocationResponse response = new GetAllShopLocationResponse();
        BeanUtils.copyProperties(shop, response);
        return response;
    }

}
