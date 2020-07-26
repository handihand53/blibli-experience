package com.blibli.experience.commandImpl.biddingOrder;

import com.blibli.experience.command.biddingOrder.GetBiddingOrderDetailCommand;
import com.blibli.experience.entity.document.BiddingOrder;
import com.blibli.experience.model.response.biddingOrder.GetBiddingOrderDetailResponse;
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
public class GetBiddingOrderDetailCommandImpl implements GetBiddingOrderDetailCommand {

    private BiddingOrderRepository biddingOrderRepository;

    @Autowired
    public GetBiddingOrderDetailCommandImpl(BiddingOrderRepository biddingOrderRepository) {
        this.biddingOrderRepository = biddingOrderRepository;
    }

    @Override
    public Mono<GetBiddingOrderDetailResponse> execute(UUID request) {
        return biddingOrderRepository.findFirstByBiddingOrderId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Bidding Order not found.")))
                .map(this::toResponse);
    }

    private GetBiddingOrderDetailResponse toResponse(BiddingOrder biddingOrder) {
        GetBiddingOrderDetailResponse response = new GetBiddingOrderDetailResponse();
        BeanUtils.copyProperties(biddingOrder, response);
        return response;
    }
}
