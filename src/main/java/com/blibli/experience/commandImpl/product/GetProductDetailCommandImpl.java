package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductDetailCommand;
import com.blibli.experience.entity.document.Product;
import com.blibli.experience.model.response.product.GetProductDetailResponse;
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
public class GetProductDetailCommandImpl implements GetProductDetailCommand {

  private ProductRepository productRepository;

  @Autowired
  public GetProductDetailCommandImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Mono<GetProductDetailResponse> execute(UUID id) {
    return productRepository.findFirstByProductId(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
        .map(this::toResponse);
  }

  private GetProductDetailResponse toResponse(Product product) {
    GetProductDetailResponse response = new GetProductDetailResponse();
    BeanUtils.copyProperties(product, response);
    return response;
  }


}
