package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductTagEnumCommand;
import com.blibli.experience.enums.ProductTag;
import com.blibli.experience.model.response.product.GetProductTagEnumResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Slf4j
@Service
public class GetProductTagEnumCommandImpl implements GetProductTagEnumCommand {

  @Override
  public Mono<GetProductTagEnumResponse> execute(String request) {
    return Mono.just(toResponse());
  }

  private GetProductTagEnumResponse toResponse() {
    GetProductTagEnumResponse response = new GetProductTagEnumResponse();
    List<ProductTag> tags = new ArrayList<ProductTag>(EnumSet.allOf(ProductTag.class));
    response.setTags(tags);
    return response;
  }

}
