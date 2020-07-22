package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.UpdateProductMasterCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.model.request.productMaster.UpdateProductMasterRequest;
import com.blibli.experience.model.response.productMaster.UpdateProductMasterResponse;
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
public class UpdateProductMasterCommandImpl implements UpdateProductMasterCommand {

    private ProductMasterRepository productMasterRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public UpdateProductMasterCommandImpl(ProductMasterRepository productMasterRepository, ProductStockRepository productStockRepository) {
        this.productMasterRepository = productMasterRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<UpdateProductMasterResponse> execute(UpdateProductMasterRequest request) {
        List<ProductStock> productStockList = GetProductStockList(request.getProductId());
        return productMasterRepository.findFirstByProductId(request.getProductId())
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found.")))
                .flatMap(productMaster -> {
                    ProductMaster updatedProductMaster = updateProductMaster(productMaster, request);
                    updateProductStockData(productStockList, updatedProductMaster);
                    return productMasterRepository.save(updatedProductMaster);
                })
                .map(this::toResponse);
    }

    private List<ProductStock> GetProductStockList(UUID productId) {
        return productStockRepository.findAllByProductDataForm_ProductId(productId)
                .collectList()
                .block();
    }

    private void updateProductStockData(List<ProductStock> productStockList, ProductMaster productMaster) {
        if(productStockList != null) {
            productStockList.forEach(productStock -> {
                BeanUtils.copyProperties(productMaster, productStock.getProductDataForm());
                productStockRepository.save(productStock).subscribe();
            });
        }
    }

    private ProductMaster updateProductMaster(ProductMaster productMaster, UpdateProductMasterRequest request) {
        BeanUtils.copyProperties(request, productMaster);
        return productMaster;
    }

    private UpdateProductMasterResponse toResponse(ProductMaster productMaster) {
        UpdateProductMasterResponse response = new UpdateProductMasterResponse();
        BeanUtils.copyProperties(productMaster, response);
        return response;
    }
}
