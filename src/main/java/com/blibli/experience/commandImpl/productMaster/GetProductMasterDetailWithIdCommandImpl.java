package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.GetProductMasterDetailWithIdCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.model.response.product.GetProductMasterDetailWithIdResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.repository.ProductStockRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
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
        List<ProductStock> productStockList = productStockRepository.findAllByProductForm_ProductId(id).collectList().block();
        return productMasterRepository.findFirstByProductId(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
                .map(productMaster -> toResponse(productMaster, productStockList));
    }

    private GetProductMasterDetailWithIdResponse toResponse(ProductMaster product, List<ProductStock> productStockList) {
        GetProductMasterDetailWithIdResponse response = new GetProductMasterDetailWithIdResponse();
        BeanUtils.copyProperties(product, response);
        response.setProductStockList(productStockList);
        return response;
    }

}
