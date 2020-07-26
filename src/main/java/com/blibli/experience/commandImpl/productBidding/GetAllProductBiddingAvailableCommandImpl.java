package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.GetAllProductBiddingAvailableCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.model.response.productBidding.GetAllProductBiddingAvailableResponse;
import com.blibli.experience.repository.ProductBiddingRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllProductBiddingAvailableCommandImpl implements GetAllProductBiddingAvailableCommand {

    private ProductBiddingRepository productBiddingRepository;

    @Autowired
    public GetAllProductBiddingAvailableCommandImpl(ProductBiddingRepository productBiddingRepository) {
        this.productBiddingRepository = productBiddingRepository;
    }

    @Override
    public Mono<List<GetAllProductBiddingAvailableResponse>> execute(Integer skipCount) {
        Long count = productBiddingRepository.countAllByAvailableStatus(ProductBiddingAvailableStatus.AVAILABLE).block();
        return productBiddingRepository.findAllByAvailableStatus(ProductBiddingAvailableStatus.AVAILABLE)
                .switchIfEmpty(Mono.error(new NotFoundException("Product Bidding not found.")))
                .skip(skipCount)
                .take(20)
                .map(productBidding -> toResponse(productBidding, count))
                .collectList();
    }

    private GetAllProductBiddingAvailableResponse toResponse(ProductBidding productBidding, Long count) {
        GetAllProductBiddingAvailableResponse response = new GetAllProductBiddingAvailableResponse();
        BeanUtils.copyProperties(productBidding, response);
        response.setProductBiddingCount(count);
        return response;
    }
}
