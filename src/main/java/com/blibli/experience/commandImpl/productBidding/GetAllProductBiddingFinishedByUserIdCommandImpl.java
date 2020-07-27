package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.GetAllProductBiddingFinishedByUserIdCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.model.response.productBidding.GetAllProductBiddingFinishedByUserIdResponse;
import com.blibli.experience.repository.ProductBiddingRepository;
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
public class GetAllProductBiddingFinishedByUserIdCommandImpl implements GetAllProductBiddingFinishedByUserIdCommand {

    private ProductBiddingRepository productBiddingRepository;

    @Autowired
    public GetAllProductBiddingFinishedByUserIdCommandImpl(ProductBiddingRepository productBiddingRepository) {
        this.productBiddingRepository = productBiddingRepository;
    }

    @Override
    public Mono<List<GetAllProductBiddingFinishedByUserIdResponse>> execute(UUID request) {
        Long count = productBiddingRepository.countAllByAvailableStatusAndUserData_UserId(ProductBiddingAvailableStatus.FINISHED, request).block();
        return productBiddingRepository.findAllByUserData_UserIdAndAvailableStatus(request, ProductBiddingAvailableStatus.FINISHED)
                .switchIfEmpty(Mono.error(new NotFoundException("Product Bidding not found.")))
                .map(productBidding -> toResponse(productBidding, count))
                .collectList();
    }

    private GetAllProductBiddingFinishedByUserIdResponse toResponse(ProductBidding productBidding, Long count) {
        GetAllProductBiddingFinishedByUserIdResponse response = new GetAllProductBiddingFinishedByUserIdResponse();
        BeanUtils.copyProperties(productBidding, response);
        response.setProductBiddingCount(count);
        return response;
    }
}
