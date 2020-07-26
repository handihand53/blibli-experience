package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetAllProductByCategoryCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.model.request.product.GetAllProductByCategoryRequest;
import com.blibli.experience.model.response.product.GetAllProductByCategoryResponse;
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
public class GetAllProductByCategoryCommandImpl implements GetAllProductByCategoryCommand {

    private ProductMasterRepository productMasterRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public GetAllProductByCategoryCommandImpl(ProductMasterRepository productMasterRepository, ProductStockRepository productStockRepository) {
        this.productMasterRepository = productMasterRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<List<GetAllProductByCategoryResponse>> execute(GetAllProductByCategoryRequest request) {
        Long count = productStockRepository.countAllByProductDataForm_ProductCategory(request.getProductCategory()).block();
        return productMasterRepository.findAllByProductCategory(request.getProductCategory())
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found.")))
                .skip(request.getSkipCount())
                .take(20)
                .flatMap(this::getProductStock)
                .map(productStock -> toResponse(productStock, count))
                .collectList();
    }

    private Mono<ProductStock> getProductStock(ProductMaster productMaster) {
        return productStockRepository.findFirstByProductDataForm_ProductId(productMaster.getProductId());
    }

    private GetAllProductByCategoryResponse toResponse(ProductStock productStock, Long count) {
        GetAllProductByCategoryResponse response = new GetAllProductByCategoryResponse();
        BeanUtils.copyProperties(productStock, response);
        response.setCountProducts(count);
        return response;
    }
}
