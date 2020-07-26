package com.blibli.experience.commandImpl.productBarter;

import com.blibli.experience.command.productBarter.GetProductBarterMetadataCommand;
import com.blibli.experience.model.response.productBarter.GetProductBarterMetadataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetProductBarterMetadataCommandImpl implements GetProductBarterMetadataCommand {
    @Override
    public Mono<GetProductBarterMetadataResponse> execute(Integer request) {
        GetProductBarterMetadataResponse response = new GetProductBarterMetadataResponse();
        return Mono.just(response);
    }
}
