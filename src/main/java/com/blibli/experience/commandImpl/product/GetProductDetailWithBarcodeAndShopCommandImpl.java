package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductDetailWithBarcodeAndShopCommand;
import com.blibli.experience.entity.document.Product;
import com.blibli.experience.model.request.product.GetProductDetailWithBarcodeAndShopRequest;
import com.blibli.experience.model.response.product.GetProductDetailWithBarcodeAndShopResponse;
import com.blibli.experience.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetProductDetailWithBarcodeAndShopCommandImpl
    implements GetProductDetailWithBarcodeAndShopCommand {

  private ProductRepository productRepository;

  @Autowired
  public GetProductDetailWithBarcodeAndShopCommandImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Mono<GetProductDetailWithBarcodeAndShopResponse> execute(
      GetProductDetailWithBarcodeAndShopRequest request) {
    return productRepository.findFirstByProductShopForm_ShopIdAndProductBarcode(
        request.getShopId(), request.getProductBarcode())
        .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
        .map(this::toResponse);
  }

  private GetProductDetailWithBarcodeAndShopResponse toResponse(Product product) {
    GetProductDetailWithBarcodeAndShopResponse
        response = new GetProductDetailWithBarcodeAndShopResponse();
    BeanUtils.copyProperties(product, response);
    return response;
  }

}
