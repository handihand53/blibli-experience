package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetAllProductAvailableCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.model.response.product.GetAllProductAvailableResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.repository.ProductStockRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllProductAvailableCommandImpl implements GetAllProductAvailableCommand {

    private ProductMasterRepository productMasterRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public GetAllProductAvailableCommandImpl(ProductMasterRepository productMasterRepository, ProductStockRepository productStockRepository) {
        this.productMasterRepository = productMasterRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<List<GetAllProductAvailableResponse>> execute(Integer skipCount) {
        return productMasterRepository.findAllByAvailableStatus(ProductAvailableStatus.AVAILABLE)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found.")))
                .skip(skipCount)
                .take(20)
                .flatMap(this::getProductStock)
                .map(this::toResponse)
                .collectList();
    }

    private Mono<ProductStock> getProductStock(ProductMaster productMaster) {
        return productStockRepository.findFirstByProductDataForm_ProductId(productMaster.getProductId());
    }

    private GetAllProductAvailableResponse toResponse(ProductStock productStock) {
        GetAllProductAvailableResponse response = new GetAllProductAvailableResponse();
        BeanUtils.copyProperties(productStock, response);
        return response;
    }
}
