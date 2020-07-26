package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.GetProductMasterMetadataCommand;
import com.blibli.experience.model.response.productMaster.GetProductMasterMetadataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetProductMasterMetadataCommandImpl implements GetProductMasterMetadataCommand {

    @Override
    public Mono<GetProductMasterMetadataResponse> execute(Integer request) {
        GetProductMasterMetadataResponse response = new GetProductMasterMetadataResponse();
        return Mono.just(response);
    }
}
