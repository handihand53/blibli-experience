package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductsWithTagCommand;
import com.blibli.experience.entity.document.Product;
import com.blibli.experience.enums.ProductTag;
import com.blibli.experience.model.response.product.GetProductsWithTagResponse;
import com.blibli.experience.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetProductsWithTagCommandImpl implements GetProductsWithTagCommand {

  private ProductRepository productRepository;

  @Autowired
  public GetProductsWithTagCommandImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Mono<List<GetProductsWithTagResponse>> execute(ProductTag tag) {
    return productRepository.findAllByProductTagsContaining(tag)
        .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
        .map(this::toResponse)
        .collectList();
  }

  private GetProductsWithTagResponse toResponse(Product product) {
    GetProductsWithTagResponse response = new GetProductsWithTagResponse();
    BeanUtils.copyProperties(product, response);
    return response;
  }

}
