package com.blibli.experience.commandImpl.productBarter;

import com.blibli.experience.command.productBarter.GetProductBarterDetailCommand;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.model.response.productBarter.GetProductBarterDetailResponse;
import com.blibli.experience.repository.ProductBarterRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetProductBarterDetailCommandImpl implements GetProductBarterDetailCommand {

    private ProductBarterRepository productBarterRepository;

    @Autowired
    public GetProductBarterDetailCommandImpl(ProductBarterRepository productBarterRepository) {
        this.productBarterRepository = productBarterRepository;
    }

    @Override
    public Mono<GetProductBarterDetailResponse> execute(UUID request) {
        return productBarterRepository.findByProductBarterId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Product barter not found.")))
                .map(this::toResponse);
    }

    private GetProductBarterDetailResponse toResponse(ProductBarter productBarter) {
        GetProductBarterDetailResponse response = new GetProductBarterDetailResponse();
        BeanUtils.copyProperties(productBarter, response);
        return response;
    }
}
