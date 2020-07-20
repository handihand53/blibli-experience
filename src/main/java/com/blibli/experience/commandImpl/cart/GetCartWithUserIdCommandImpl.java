package com.blibli.experience.commandImpl.cart;

import com.blibli.experience.command.cart.GetCartWithUserIdCommand;
import com.blibli.experience.entity.document.Cart;
import com.blibli.experience.model.response.cart.GetCartWithUserIdResponse;
import com.blibli.experience.repository.CartRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetCartWithUserIdCommandImpl implements GetCartWithUserIdCommand {

    private CartRepository cartRepository;

    @Autowired
    public GetCartWithUserIdCommandImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Mono<GetCartWithUserIdResponse> execute(UUID request) {
        return cartRepository.findFirstByUserId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Cart not found!")))
                .map(this::toResponse);
    }

    private GetCartWithUserIdResponse toResponse(Cart cart) {
        GetCartWithUserIdResponse response = new GetCartWithUserIdResponse();
        BeanUtils.copyProperties(cart, response);
        return response;
    }


}
