package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.GetAllBarterOrderByUserIdCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.enums.BarterRoleEnum;
import com.blibli.experience.model.request.barterOrder.GetAllBarterOrderByUserIdRequest;
import com.blibli.experience.model.response.barterOrder.GetAllBarterOrderByUserIdResponse;
import com.blibli.experience.repository.BarterOrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllBarterOrderByUserIdCommandImpl implements GetAllBarterOrderByUserIdCommand {

    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public GetAllBarterOrderByUserIdCommandImpl(BarterOrderRepository barterOrderRepository) {
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<List<GetAllBarterOrderByUserIdResponse>> execute(GetAllBarterOrderByUserIdRequest request) {
        if(request.getBarterRoleEnum().equals(BarterRoleEnum.SELLER)) {
            return barterOrderRepository.findAllBySellerData_UserId(request.getUserId())
                    .switchIfEmpty(Mono.error(new NotFoundException("Barter order empty.")))
                    .map(this::toResponse)
                    .collectList();
        }else if(request.getBarterRoleEnum().equals(BarterRoleEnum.BUYER)) {
            return barterOrderRepository.findAllByBuyerData_UserId(request.getUserId())
                    .switchIfEmpty(Mono.error(new NotFoundException("Barter order empty.")))
                    .map(this::toResponse)
                    .collectList();
        }
        return Mono.error(new NotFoundException("Barter role incorrect."));
    }

    private GetAllBarterOrderByUserIdResponse toResponse(BarterOrder barterOrder) {
        GetAllBarterOrderByUserIdResponse response = new GetAllBarterOrderByUserIdResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }
}
