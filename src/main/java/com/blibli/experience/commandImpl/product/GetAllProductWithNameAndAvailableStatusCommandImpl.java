package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetAllProductWithNameAndAvailableStatusCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.model.response.product.GetAllProductWithNameAndAvailableStatusResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.repository.ProductStockRepository;
import com.blibli.experience.util.SearchKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllProductWithNameAndAvailableStatusCommandImpl implements GetAllProductWithNameAndAvailableStatusCommand {

    private ProductMasterRepository productMasterRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public GetAllProductWithNameAndAvailableStatusCommandImpl(ProductMasterRepository productMasterRepository, ProductStockRepository productStockRepository) {
        this.productMasterRepository = productMasterRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<List<GetAllProductWithNameAndAvailableStatusResponse>> execute(String searchKey) {
        Long count = productMasterRepository.countAllByProductNameContainingAndAvailableStatus(SearchKeyUtil.capitalizeSearchKey(searchKey), ProductAvailableStatus.AVAILABLE).block();
        return productMasterRepository.findAllByProductNameContainingAndAvailableStatus(SearchKeyUtil.capitalizeSearchKey(searchKey), ProductAvailableStatus.AVAILABLE)
                .switchIfEmpty(productMasterRepository.findAllByProductNameContainingAndAvailableStatus(SearchKeyUtil.lowerSearchKey(searchKey), ProductAvailableStatus.AVAILABLE))
                .flatMap(this::getProductStock)
                .map(productStock -> toResponse(productStock, count))
                .collectList();
    }

    private Mono<ProductStock> getProductStock(ProductMaster productMaster) {
        return productStockRepository.findFirstByProductDataForm_ProductId(productMaster.getProductId());
    }

    private GetAllProductWithNameAndAvailableStatusResponse toResponse(ProductStock productStock, Long count) {
        GetAllProductWithNameAndAvailableStatusResponse response = new GetAllProductWithNameAndAvailableStatusResponse();
        BeanUtils.copyProperties(productStock, response);
        response.setCountProducts(count);
        return response;
    }

}
