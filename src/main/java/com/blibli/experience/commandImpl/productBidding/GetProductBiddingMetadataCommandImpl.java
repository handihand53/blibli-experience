package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.GetProductBiddingMetadataCommand;
import com.blibli.experience.model.response.productBidding.GetProductBiddingMetadataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetProductBiddingMetadataCommandImpl implements GetProductBiddingMetadataCommand {

    @Override
    public Mono<GetProductBiddingMetadataResponse> execute(Integer request) {
        GetProductBiddingMetadataResponse response = new GetProductBiddingMetadataResponse();
        return Mono.just(response);
    }
}
