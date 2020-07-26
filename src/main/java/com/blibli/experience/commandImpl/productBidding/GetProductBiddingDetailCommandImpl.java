package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.GetProductBiddingDetailCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.model.response.productBidding.GetProductBiddingDetailResponse;
import com.blibli.experience.repository.ProductBiddingRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetProductBiddingDetailCommandImpl implements GetProductBiddingDetailCommand {

    private ProductBiddingRepository productBiddingRepository;

    @Autowired
    public GetProductBiddingDetailCommandImpl(ProductBiddingRepository productBiddingRepository) {
        this.productBiddingRepository = productBiddingRepository;
    }

    @Override
    public Mono<GetProductBiddingDetailResponse> execute(UUID request) {
        return productBiddingRepository.findFirstByProductBiddingId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Product Bidding not found.")))
                .map(this::toResponse);
    }

    private GetProductBiddingDetailResponse toResponse(ProductBidding productBidding) {
        GetProductBiddingDetailResponse response = new GetProductBiddingDetailResponse();
        BeanUtils.copyProperties(productBidding, response);
        return response;
    }
}
