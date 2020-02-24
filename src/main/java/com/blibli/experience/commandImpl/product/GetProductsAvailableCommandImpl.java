package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductsAvailableCommand;
import com.blibli.experience.entity.document.Product;
import com.blibli.experience.model.response.product.GetProductAvailableResponse;
import com.blibli.experience.repository.ProductRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GetProductsAvailableCommandImpl implements GetProductsAvailableCommand {

  private ProductRepository productRepository;

  @Autowired
  public GetProductsAvailableCommandImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Mono<List<GetProductAvailableResponse>> execute(Integer count) {
    return productRepository.findAllByProductStockGreaterThanEqual(1)
        .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
        .take(count)
        .map(this::toResponse)
        .collectList();
  }

  private GetProductAvailableResponse toResponse(Product product) {
    GetProductAvailableResponse response = new GetProductAvailableResponse();
    BeanUtils.copyProperties(product, response);
    return response;
  }

}
