package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductCategoryEnumCommand;
import com.blibli.experience.enums.ProductCategory;
import com.blibli.experience.model.response.product.GetProductCategoryEnumResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Slf4j
@Service
public class GetProductCategoryEnumCommandImpl implements GetProductCategoryEnumCommand {

  @Override
  public Mono<GetProductCategoryEnumResponse> execute(String request) {
    return Mono.just(toResponse());
  }

  private GetProductCategoryEnumResponse toResponse() {
    GetProductCategoryEnumResponse response = new GetProductCategoryEnumResponse();
    List<ProductCategory> categories = new ArrayList<>(EnumSet.allOf(ProductCategory.class));
    response.setCategories(categories);
    return response;
  }

}
