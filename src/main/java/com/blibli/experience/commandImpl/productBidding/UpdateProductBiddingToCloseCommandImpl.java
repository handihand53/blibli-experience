package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.UpdateProductBiddingToCloseCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.model.response.productBidding.UpdateProductBiddingToBidResponse;
import com.blibli.experience.model.response.productBidding.UpdateProductBiddingToCloseResponse;
import com.blibli.experience.repository.ProductBiddingRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UpdateProductBiddingToCloseCommandImpl implements UpdateProductBiddingToCloseCommand {

    private ProductBiddingRepository productBiddingRepository;

    @Autowired
    public UpdateProductBiddingToCloseCommandImpl(ProductBiddingRepository productBiddingRepository) {
        this.productBiddingRepository = productBiddingRepository;
    }

    @Override
    public Mono<List<UpdateProductBiddingToCloseResponse>> execute(Integer request) {
        return productBiddingRepository.findAllByCloseBidDateBefore(LocalDateTime.now())
                .switchIfEmpty(Mono.error(new NotFoundException("No Bid closed yet.")))
                .map(this::updateProductBidding)
                .flatMap(productBidding -> productBiddingRepository.save(productBidding))
                .map(this::toResponse)
                .collectList();
    }

    private ProductBidding updateProductBidding(ProductBidding productBidding) {
        productBidding.setAvailableStatus(ProductBiddingAvailableStatus.FINISHED);
        return productBidding;
    }

    private UpdateProductBiddingToCloseResponse toResponse(ProductBidding productBidding) {
        UpdateProductBiddingToCloseResponse response = new UpdateProductBiddingToCloseResponse();
        BeanUtils.copyProperties(productBidding, response);
        return response;
    }
}
