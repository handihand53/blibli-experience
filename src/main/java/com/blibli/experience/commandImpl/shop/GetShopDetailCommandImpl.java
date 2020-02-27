package com.blibli.experience.commandImpl.shop;

import com.blibli.experience.command.shop.GetShopDetailCommand;
import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.model.response.shop.GetShopDetailResponse;
import com.blibli.experience.repository.ShopRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetShopDetailCommandImpl implements GetShopDetailCommand {

  private ShopRepository shopRepository;

  @Autowired
  public GetShopDetailCommandImpl(ShopRepository shopRepository) {
    this.shopRepository = shopRepository;
  }

  @Override
  public Mono<GetShopDetailResponse> execute(UUID userId) {
    return shopRepository.findFirstByUserId(userId)
        .switchIfEmpty(Mono.error(new NotFoundException("Shop not found!")))
        .map(this::toResponse);
  }

  private GetShopDetailResponse toResponse(Shop shop) {
    GetShopDetailResponse response = new GetShopDetailResponse();
    BeanUtils.copyProperties(shop, response);
    return response;
  }

}
