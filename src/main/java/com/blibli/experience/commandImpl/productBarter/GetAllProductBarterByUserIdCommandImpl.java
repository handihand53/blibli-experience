package com.blibli.experience.commandImpl.productBarter;

import com.blibli.experience.command.productBarter.GetAllProductBarterByUserIdCommand;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.model.response.productBarter.GetAllProductBarterByUserIdResponse;
import com.blibli.experience.repository.ProductBarterRepository;
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
public class GetAllProductBarterByUserIdCommandImpl implements GetAllProductBarterByUserIdCommand {

    private ProductBarterRepository productBarterRepository;

    @Autowired
    public GetAllProductBarterByUserIdCommandImpl(ProductBarterRepository productBarterRepository) {
        this.productBarterRepository = productBarterRepository;
    }

    @Override
    public Mono<List<GetAllProductBarterByUserIdResponse>> execute(UUID request) {
        return productBarterRepository.findAllByUserData_UserId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Product barter not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllProductBarterByUserIdResponse toResponse(ProductBarter productBarter) {
        GetAllProductBarterByUserIdResponse response = new GetAllProductBarterByUserIdResponse();
        BeanUtils.copyProperties(productBarter, response);
        return response;
    }
}
