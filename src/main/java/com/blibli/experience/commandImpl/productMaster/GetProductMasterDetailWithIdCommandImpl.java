package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.GetProductMasterDetailWithIdCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.model.response.product.GetProductMasterDetailWithIdResponse;
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
public class GetProductMasterDetailWithIdCommandImpl implements GetProductMasterDetailWithIdCommand {

    private ProductMasterRepository productMasterRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public GetProductMasterDetailWithIdCommandImpl(ProductMasterRepository productMasterRepository, ProductStockRepository productStockRepository) {
        this.productMasterRepository = productMasterRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<GetProductMasterDetailWithIdResponse> execute(UUID id) {
        return productMasterRepository.findFirstByProductId(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
                .map(this::toResponse)
                .flatMap(this::addStockDataToResponse);
    }

    private GetProductMasterDetailWithIdResponse toResponse(ProductMaster product) {
        GetProductMasterDetailWithIdResponse response = new GetProductMasterDetailWithIdResponse();
        BeanUtils.copyProperties(product, response);
        return response;
    }

    private Mono<GetProductMasterDetailWithIdResponse> addStockDataToResponse(GetProductMasterDetailWithIdResponse response) {
        return productStockRepository.findAllByProductDataForm_ProductId(response.getProductId())
                .collectList()
                .map(productStockList -> {
                    response.setProductStockList(productStockList);
                    return response;
                });
    }

}
