package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.GetAllProductBiddingByAvailableAndCategoryCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.model.request.productBidding.GetAllProductBiddingByAvailableAndCategoryRequest;
import com.blibli.experience.model.response.productBidding.GetAllProductBiddingByAvailableAndCategoryResponse;
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
public class GetAllProductBiddingByAvailableAndCategoryCommandImpl implements GetAllProductBiddingByAvailableAndCategoryCommand {

    private ProductBiddingRepository productBiddingRepository;

    @Autowired
    public GetAllProductBiddingByAvailableAndCategoryCommandImpl(ProductBiddingRepository productBiddingRepository) {
        this.productBiddingRepository = productBiddingRepository;
    }

    @Override
    public Mono<List<GetAllProductBiddingByAvailableAndCategoryResponse>> execute(GetAllProductBiddingByAvailableAndCategoryRequest request) {
        Long count = productBiddingRepository.countAllByAvailableStatusAndProductCategory(ProductBiddingAvailableStatus.AVAILABLE, request.getProductCategory())
                .block();
        return productBiddingRepository.findAllByAvailableStatusAndProductCategory(ProductBiddingAvailableStatus.AVAILABLE, request.getProductCategory())
                .switchIfEmpty(Mono.error(new NotFoundException("Product Bidding not found.")))
                .skip(request.getSkipCount())
                .map(productBidding -> toResponse(productBidding, count))
                .collectList();
    }

    private GetAllProductBiddingByAvailableAndCategoryResponse toResponse(ProductBidding productBidding, Long count) {
        GetAllProductBiddingByAvailableAndCategoryResponse response = new GetAllProductBiddingByAvailableAndCategoryResponse();
        BeanUtils.copyProperties(productBidding, response);
        response.setProductBiddingCount(count);
        return response;
    }
}
