package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductDetailWithIdCommand;
import com.blibli.experience.entity.document.Product;
import com.blibli.experience.model.response.product.GetProductDetailWithIdResponse;
import com.blibli.experience.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetProductDetailWithIdCommandImpl implements GetProductDetailWithIdCommand {

  private ProductRepository productRepository;

  @Autowired
  public GetProductDetailWithIdCommandImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Mono<GetProductDetailWithIdResponse> execute(UUID id) {
    return productRepository.findFirstByProductId(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
        .map(this::toResponse);
  }

  private GetProductDetailWithIdResponse toResponse(Product product) {
    GetProductDetailWithIdResponse response = new GetProductDetailWithIdResponse();
    BeanUtils.copyProperties(product, response);
    return response;
  }


}
