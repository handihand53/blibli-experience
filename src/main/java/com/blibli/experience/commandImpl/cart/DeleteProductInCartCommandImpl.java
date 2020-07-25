package com.blibli.experience.commandImpl.cart;

import com.blibli.experience.command.cart.DeleteProductInCartCommand;
import com.blibli.experience.entity.document.Cart;
import com.blibli.experience.entity.form.CartForm;
import com.blibli.experience.model.request.cart.DeleteProductInCartRequest;
import com.blibli.experience.model.response.cart.DeleteProductInCartResponse;
import com.blibli.experience.repository.CartRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DeleteProductInCartCommandImpl implements DeleteProductInCartCommand {

    private CartRepository cartRepository;

    @Autowired
    public DeleteProductInCartCommandImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Mono<DeleteProductInCartResponse> execute(DeleteProductInCartRequest request) {
        return cartRepository.findFirstByCartId(request.getCartId())
                .switchIfEmpty(Mono.error(new NotFoundException("Cart not found.")))
                .map(cart -> updateCart(cart, request))
                .flatMap(cart -> cartRepository.save(cart))
                .map(this::toResponse);
    }

    private Cart updateCart(Cart cart, DeleteProductInCartRequest request) {
        List<CartForm> cartForms = new ArrayList<>();
        cart.getCartForms().forEach(cartForm -> {
            if(!checkProductToRemoved(cartForm, request)) {
                cartForms.add(cartForm);
            }
        });
        cart.setCartForms(cartForms);
        cart.setLastUpdated(LocalDateTime.now());
        return cart;
    }

    private Boolean checkProductToRemoved(CartForm cartForm, DeleteProductInCartRequest request) {
        return cartForm.getStockForm().getStockId().equals(request.getStockId());
    }

    private DeleteProductInCartResponse toResponse(Cart cart) {
        DeleteProductInCartResponse response = new DeleteProductInCartResponse();
        BeanUtils.copyProperties(cart, response);
        return response;
    }
}
