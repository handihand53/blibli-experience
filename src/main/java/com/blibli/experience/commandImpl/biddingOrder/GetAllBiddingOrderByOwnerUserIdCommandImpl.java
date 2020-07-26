package com.blibli.experience.commandImpl.biddingOrder;

import com.blibli.experience.command.biddingOrder.GetAllBiddingOrderByOwnerUserIdCommand;
import com.blibli.experience.entity.document.BiddingOrder;
import com.blibli.experience.model.response.biddingOrder.GetAllBiddingOrderByOwnerUserIdResponse;
import com.blibli.experience.repository.BiddingOrderRepository;
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
public class GetAllBiddingOrderByOwnerUserIdCommandImpl implements GetAllBiddingOrderByOwnerUserIdCommand {

    private BiddingOrderRepository biddingOrderRepository;

    @Autowired
    public GetAllBiddingOrderByOwnerUserIdCommandImpl(BiddingOrderRepository biddingOrderRepository) {
        this.biddingOrderRepository = biddingOrderRepository;
    }

    @Override
    public Mono<List<GetAllBiddingOrderByOwnerUserIdResponse>> execute(UUID request) {
        return biddingOrderRepository.findAllByBiddingOwner_UserId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Bidding Order not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllBiddingOrderByOwnerUserIdResponse toResponse(BiddingOrder biddingOrder) {
        GetAllBiddingOrderByOwnerUserIdResponse response = new GetAllBiddingOrderByOwnerUserIdResponse();
        BeanUtils.copyProperties(biddingOrder, response);
        return response;
    }
}
