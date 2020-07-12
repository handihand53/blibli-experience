package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.GetProductMasterDetailWithId;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.model.response.product.GetProductDetailWithIdAndShopIdResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetProductMasterDetailWithIdImpl implements GetProductMasterDetailWithId {

  private ProductMasterRepository productMasterRepository;

  @Autowired
  public GetProductMasterDetailWithIdImpl(ProductMasterRepository productMasterRepository) {
    this.productMasterRepository = productMasterRepository;
  }

  @Override
  public Mono<GetProductDetailWithIdAndShopIdResponse> execute(UUID id) {
    return productMasterRepository.findFirstByProductId(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
        .map(this::toResponse);
  }

  private GetProductDetailWithIdAndShopIdResponse toResponse(ProductMaster product) {
    GetProductDetailWithIdAndShopIdResponse response = new GetProductDetailWithIdAndShopIdResponse();
    BeanUtils.copyProperties(product, response);
    return response;
  }

}
