package com.blibli.experience.commandImpl.productStock;

import com.blibli.experience.command.productStock.UpdateProductStockCommand;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.model.request.productStock.UpdateProductStockRequest;
import com.blibli.experience.model.response.productStock.UpdateProductStockResponse;
import com.blibli.experience.repository.ProductStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UpdateProductStockCommandImpl implements UpdateProductStockCommand {

    private ProductStockRepository productStockRepository;

    public UpdateProductStockCommandImpl(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<UpdateProductStockResponse> execute(UpdateProductStockRequest request) {
        return productStockRepository.findFirstByStockId(request.getStockId())
                .map(productStock -> updateStock(request, productStock))
                .flatMap(productStock -> productStockRepository.save(productStock))
                .map(this::toResponse);
    }

    private ProductStock updateStock(UpdateProductStockRequest request, ProductStock productStock) {
        productStock.setProductStock(productStock.getProductStock() + request.getAddedStock());
        return productStock;
    }

    private UpdateProductStockResponse toResponse(ProductStock productStock) {
        UpdateProductStockResponse response = new UpdateProductStockResponse();
        BeanUtils.copyProperties(productStock, response);
        return response;
    }

}
