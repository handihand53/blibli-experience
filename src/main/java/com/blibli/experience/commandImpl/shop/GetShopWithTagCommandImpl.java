package com.blibli.experience.commandImpl.shop;

import com.blibli.experience.command.shop.GetShopWithTagCommand;
import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.enums.ShopTag;
import com.blibli.experience.model.response.shop.GetShopWithTagResponse;
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
public class GetShopWithTagCommandImpl implements GetShopWithTagCommand {

  private ShopRepository shopRepository;

  @Autowired
  public GetShopWithTagCommandImpl(ShopRepository shopRepository) {
    this.shopRepository = shopRepository;
  }

  @Override
  public Mono<List<GetShopWithTagResponse>> execute(ShopTag tag) {
    return shopRepository.findAllByShopTagsContaining(tag)
        .switchIfEmpty(Mono.error(new NotFoundException("Shop not found!")))
        .map(this::toResponse)
        .collectList();
  }

  private GetShopWithTagResponse toResponse(Shop shop) {
    GetShopWithTagResponse response = new GetShopWithTagResponse();
    BeanUtils.copyProperties(shop, response);
    return response;
  }

}
