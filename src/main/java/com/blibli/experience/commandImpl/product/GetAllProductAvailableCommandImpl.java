package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetAllProductAvailableCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.model.response.product.GetAllProductAvailableResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllProductAvailableCommandImpl implements GetAllProductAvailableCommand {

    private ProductMasterRepository productMasterRepository;

    @Autowired
    public GetAllProductAvailableCommandImpl(ProductMasterRepository productMasterRepository) {
        this.productMasterRepository = productMasterRepository;
    }

    @Override
    public Mono<List<GetAllProductAvailableResponse>> execute(Integer skipCount) {
        return productMasterRepository.findAllByAvailableFlagTrue()
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found.")))
                .skip(skipCount)
                .take(20)
                .map(this::toResponse)
                .collectList();
    }

    private GetAllProductAvailableResponse toResponse(ProductMaster productMaster) {
        GetAllProductAvailableResponse response = new GetAllProductAvailableResponse();
        BeanUtils.copyProperties(productMaster, response);
        return response;
    }
}
