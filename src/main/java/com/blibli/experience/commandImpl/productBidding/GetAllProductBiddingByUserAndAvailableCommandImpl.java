package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.GetAllProductBiddingByUserAndAvailableCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.model.response.productBidding.GetAllProductBiddingByUserAndAvailableResponse;
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
public class GetAllProductBiddingByUserAndAvailableCommandImpl implements GetAllProductBiddingByUserAndAvailableCommand {

    private ProductBiddingRepository productBiddingRepository;

    @Autowired
    public GetAllProductBiddingByUserAndAvailableCommandImpl(ProductBiddingRepository productBiddingRepository) {
        this.productBiddingRepository = productBiddingRepository;
    }

    @Override
    public Mono<List<GetAllProductBiddingByUserAndAvailableResponse>> execute(UUID request) {
        return productBiddingRepository.findAllByUserData_UserIdAndAvailableStatus(request, ProductBiddingAvailableStatus.AVAILABLE)
                .switchIfEmpty(Mono.error(new NotFoundException("Product Bidding not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllProductBiddingByUserAndAvailableResponse toResponse(ProductBidding productBidding) {
        GetAllProductBiddingByUserAndAvailableResponse response = new GetAllProductBiddingByUserAndAvailableResponse();
        BeanUtils.copyProperties(productBidding, response);
        return response;
    }
}
