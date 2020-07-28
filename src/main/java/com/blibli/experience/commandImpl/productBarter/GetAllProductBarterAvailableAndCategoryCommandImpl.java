package com.blibli.experience.commandImpl.productBarter;

import com.blibli.experience.command.productBarter.GetAllProductBarterAvailableAndCategoryCommand;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.model.request.productBarter.GetAllProductBarterAvailableAndCategoryRequest;
import com.blibli.experience.model.response.productBarter.GetAllProductBarterAvailableAndCategoryResponse;
import com.blibli.experience.repository.ProductBarterRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllProductBarterAvailableAndCategoryCommandImpl implements GetAllProductBarterAvailableAndCategoryCommand {

    private ProductBarterRepository productBarterRepository;

    @Autowired
    public GetAllProductBarterAvailableAndCategoryCommandImpl(ProductBarterRepository productBarterRepository) {
        this.productBarterRepository = productBarterRepository;
    }

    @Override
    public Mono<List<GetAllProductBarterAvailableAndCategoryResponse>> execute(GetAllProductBarterAvailableAndCategoryRequest request) {
        Long count = productBarterRepository.countAllByAvailableStatusAndProductCategory(ProductAvailableStatus.AVAILABLE, request.getProductCategory()).block();
        return productBarterRepository.findAllByAvailableStatusAndProductCategory(ProductAvailableStatus.AVAILABLE, request.getProductCategory())
                .switchIfEmpty(Mono.error(new NotFoundException("Product Barter not found.")))
                .skip(request.getSkipCount())
                .map(productBarter -> toResponse(productBarter, count))
                .collectList();
    }

    private GetAllProductBarterAvailableAndCategoryResponse toResponse(ProductBarter productBarter, Long count) {
        GetAllProductBarterAvailableAndCategoryResponse response = new GetAllProductBarterAvailableAndCategoryResponse();
        BeanUtils.copyProperties(productBarter, response);
        response.setCount(count);
        return response;
    }

}
