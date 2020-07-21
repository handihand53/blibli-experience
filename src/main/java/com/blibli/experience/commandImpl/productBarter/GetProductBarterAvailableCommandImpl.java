package com.blibli.experience.commandImpl.productBarter;

import com.blibli.experience.command.productBarter.GetProductBarterAvailableCommand;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.model.response.productBarter.GetProductBarterAvailableResponse;
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
public class GetProductBarterAvailableCommandImpl implements GetProductBarterAvailableCommand {

    private ProductBarterRepository productBarterRepository;

    @Autowired
    public GetProductBarterAvailableCommandImpl(ProductBarterRepository productBarterRepository) {
        this.productBarterRepository = productBarterRepository;
    }

    @Override
    public Mono<List<GetProductBarterAvailableResponse>> execute(Integer skipCount) {
        return productBarterRepository.findAllByAvailableStatus(ProductAvailableStatus.AVAILABLE)
                .switchIfEmpty(Mono.error(new NotFoundException("Product Barter not found.")))
                .skip(skipCount)
                .take(20)
                .map(this::toResponse)
                .collectList();
    }

    private GetProductBarterAvailableResponse toResponse(ProductBarter productBarter) {
        GetProductBarterAvailableResponse response = new GetProductBarterAvailableResponse();
        BeanUtils.copyProperties(productBarter, response);
        return response;
    }
}
