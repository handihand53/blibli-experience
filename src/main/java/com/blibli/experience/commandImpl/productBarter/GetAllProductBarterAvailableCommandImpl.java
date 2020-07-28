package com.blibli.experience.commandImpl.productBarter;

import com.blibli.experience.command.productBarter.GetAllProductBarterAvailableCommand;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.model.response.productBarter.GetAllProductBarterAvailableResponse;
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
public class GetAllProductBarterAvailableCommandImpl implements GetAllProductBarterAvailableCommand {

    private ProductBarterRepository productBarterRepository;

    @Autowired
    public GetAllProductBarterAvailableCommandImpl(ProductBarterRepository productBarterRepository) {
        this.productBarterRepository = productBarterRepository;
    }

    @Override
    public Mono<List<GetAllProductBarterAvailableResponse>> execute(Integer skipCount) {
        Long count = productBarterRepository.countAllByAvailableStatus(ProductAvailableStatus.AVAILABLE).block();
        return productBarterRepository.findAllByAvailableStatus(ProductAvailableStatus.AVAILABLE)
                .switchIfEmpty(Mono.error(new NotFoundException("Product Barter not found.")))
                .skip(skipCount)
                .take(20)
                .map(productBarter -> toResponse(productBarter, count))
                .collectList();
    }

    private GetAllProductBarterAvailableResponse toResponse(ProductBarter productBarter, Long count) {
        GetAllProductBarterAvailableResponse response = new GetAllProductBarterAvailableResponse();
        BeanUtils.copyProperties(productBarter, response);
        response.setCount(count);
        return response;
    }
}
