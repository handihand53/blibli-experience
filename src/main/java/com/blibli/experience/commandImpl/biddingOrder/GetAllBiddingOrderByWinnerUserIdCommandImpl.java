package com.blibli.experience.commandImpl.biddingOrder;

import com.blibli.experience.command.biddingOrder.GetAllBiddingOrderByWinnerUserIdCommand;
import com.blibli.experience.entity.document.BiddingOrder;
import com.blibli.experience.model.response.biddingOrder.GetAllBiddingOrderByWinnerUserIdResponse;
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
public class GetAllBiddingOrderByWinnerUserIdCommandImpl implements GetAllBiddingOrderByWinnerUserIdCommand {

    private BiddingOrderRepository biddingOrderRepository;

    @Autowired
    public GetAllBiddingOrderByWinnerUserIdCommandImpl(BiddingOrderRepository biddingOrderRepository) {
        this.biddingOrderRepository = biddingOrderRepository;
    }

    @Override
    public Mono<List<GetAllBiddingOrderByWinnerUserIdResponse>> execute(UUID request) {
        return biddingOrderRepository.findAllByBiddingWinner_UserId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Bidding Order not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllBiddingOrderByWinnerUserIdResponse toResponse(BiddingOrder biddingOrder) {
        GetAllBiddingOrderByWinnerUserIdResponse response = new GetAllBiddingOrderByWinnerUserIdResponse();
        BeanUtils.copyProperties(biddingOrder, response);
        return response;
    }
}
