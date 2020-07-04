package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductDetailWithIdAndShopIdCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.model.response.product.GetProductDetailWithIdResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.repository.ProductStockRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetProductDetailWithIdAndShopIdCommandImpl implements GetProductDetailWithIdAndShopIdCommand {

  private ProductMasterRepository productMasterRepository;
  private ProductStockRepository productStockRepository;

  @Autowired
  public GetProductDetailWithIdAndShopIdCommandImpl(ProductMasterRepository productMasterRepository,
                                                    ProductStockRepository productStockRepository) {
    this.productMasterRepository = productMasterRepository;
    this.productStockRepository = productStockRepository;
  }

  @Override
  public Mono<GetProductDetailWithIdResponse> execute(UUID id) {
    return productMasterRepository.findFirstByProductId(id)
        .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
        .map(this::toResponse);
  }

  private GetProductDetailWithIdResponse toResponse(ProductMaster product) {
    GetProductDetailWithIdResponse response = new GetProductDetailWithIdResponse();
    ProductStock stock = productStockRepository.findFirstByProductShopForm_ShopId(product.getProductId())
            .switchIfEmpty(Mono.error(new NotFoundException("This shop didn't have this product"))).block();
    BeanUtils.copyProperties(product, response);
    BeanUtils.copyProperties(stock, response);
    return response;
  }


}
