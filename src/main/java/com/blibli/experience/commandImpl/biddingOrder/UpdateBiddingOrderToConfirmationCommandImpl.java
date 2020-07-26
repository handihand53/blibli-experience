package com.blibli.experience.commandImpl.biddingOrder;

import com.blibli.experience.command.biddingOrder.UpdateBiddingOrderToConfirmationCommand;
import com.blibli.experience.entity.document.BiddingOrder;
import com.blibli.experience.enums.BiddingOrderStatus;
import com.blibli.experience.model.response.biddingOrder.UpdateBiddingOrderToConfirmationResponse;
import com.blibli.experience.repository.BiddingOrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class UpdateBiddingOrderToConfirmationCommandImpl implements UpdateBiddingOrderToConfirmationCommand {

    private BiddingOrderRepository biddingOrderRepository;

    @Autowired
    public UpdateBiddingOrderToConfirmationCommandImpl(BiddingOrderRepository biddingOrderRepository) {
        this.biddingOrderRepository = biddingOrderRepository;
    }

    @Override
    public Mono<UpdateBiddingOrderToConfirmationResponse> execute(UUID request) {
        return biddingOrderRepository.findFirstByBiddingOrderId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Bidding Order not found.")))
                .map(this::updateBiddingOrder)
                .flatMap(biddingOrder -> biddingOrderRepository.save(biddingOrder))
                .map(this::toResponse);
    }

    private BiddingOrder updateBiddingOrder(BiddingOrder biddingOrder) {
        if(checkBiddingOrderStatus(biddingOrder)) {
            biddingOrder.setBiddingOrderStatus(BiddingOrderStatus.PAID_TO_WINNER);
            return biddingOrder;
        } else {
            throw new RuntimeException("This item is not delivered yet.");
        }
    }

    private Boolean checkBiddingOrderStatus(BiddingOrder biddingOrder) {
        return biddingOrder.getBiddingOrderStatus().equals(BiddingOrderStatus.DELIVERED_TO_BIDDING_OWNER);
    }

    private UpdateBiddingOrderToConfirmationResponse toResponse(BiddingOrder biddingOrder) {
        UpdateBiddingOrderToConfirmationResponse response = new UpdateBiddingOrderToConfirmationResponse();
        BeanUtils.copyProperties(biddingOrder, response);
        return response;
    }
}
