package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.GetAllProductBiddingBidByUserCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.entity.form.BiddingForm;
import com.blibli.experience.enums.BidStatus;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.model.response.productBidding.GetAllProductBiddingBidByUserResponse;
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
public class GetAllProductBiddingBidByUserCommandImpl implements GetAllProductBiddingBidByUserCommand {

    private ProductBiddingRepository productBiddingRepository;

    @Autowired
    public GetAllProductBiddingBidByUserCommandImpl(ProductBiddingRepository productBiddingRepository) {
        this.productBiddingRepository = productBiddingRepository;
    }

    @Override
    public Mono<List<GetAllProductBiddingBidByUserResponse>> execute(UUID request) {
        return productBiddingRepository.findAllByBiddingFormsUserData(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Product Bidding not found.")))
                .filter(this::filterByAvailableStatus)
                .map(productBidding -> toResponse(productBidding, request))
                .collectList();
    }

    private Boolean filterByAvailableStatus(ProductBidding productBidding) {
        return productBidding.getAvailableStatus().equals(ProductBiddingAvailableStatus.AVAILABLE);
    }

    private GetAllProductBiddingBidByUserResponse toResponse(ProductBidding productBidding, UUID request) {
        GetAllProductBiddingBidByUserResponse response = new GetAllProductBiddingBidByUserResponse();
        BeanUtils.copyProperties(productBidding, response);
        response.setBidStatus(getBidStatus(productBidding, request));
        return response;
    }

    private BidStatus getBidStatus(ProductBidding productBidding, UUID request) {
        for (BiddingForm biddingForm : productBidding.getBiddingForms()) {
            if(productBidding.getCurrentPrice().equals(biddingForm.getBiddingPrice()) &&
            request.equals(biddingForm.getUserDataForm().getUserId())) {
                return BidStatus.WINNING;
            }
        }
        return BidStatus.LOSING;
    }
}
