package com.blibli.experience.commandImpl.cart;

import com.blibli.experience.command.cart.UpdateCartProductAmountCommand;
import com.blibli.experience.entity.document.Cart;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.entity.form.CartForm;
import com.blibli.experience.model.request.cart.UpdateCartProductAmountRequest;
import com.blibli.experience.model.response.cart.UpdateCartProductAmountResponse;
import com.blibli.experience.repository.CartRepository;
import com.blibli.experience.repository.ProductStockRepository;
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
public class UpdateCartProductAmountCommandImpl implements UpdateCartProductAmountCommand {

    private CartRepository cartRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public UpdateCartProductAmountCommandImpl(CartRepository cartRepository, ProductStockRepository productStockRepository) {
        this.cartRepository = cartRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<UpdateCartProductAmountResponse> execute(UpdateCartProductAmountRequest request) {
        ProductStock productStock = getProductStock(request);
        return cartRepository.findFirstByCartId(request.getCartId())
                .switchIfEmpty(Mono.error(new NotFoundException("Cart not found.")))
                .map(cart -> updateCart(cart, productStock, request))
                .flatMap(cart -> cartRepository.save(cart))
                .map(this::toResponse);
    }

    private ProductStock getProductStock(UpdateCartProductAmountRequest request) {
        ProductStock productStock = productStockRepository.findFirstByStockId(request.getStockId()).block();
        if (productStock != null) {
            return productStock;
        } else {
            throw new RuntimeException("Product Stock not found.");
        }
    }

    private Cart updateCart(Cart cart, ProductStock productStock, UpdateCartProductAmountRequest request) {
        List<CartForm> cartForms = new ArrayList<>();
            cart.getCartForms().forEach(cartForm ->
                    cartForms.add(checkAndUpdateAmountInCartForm(productStock, cartForm, request)));
            cart.setCartForms(cartForms);
            cart.setLastUpdated(LocalDateTime.now());
            return cart;
    }

    private CartForm checkAndUpdateAmountInCartForm(ProductStock productStock, CartForm cartForm, UpdateCartProductAmountRequest request) {
        if (cartForm.getStockForm().getStockId().equals(request.getStockId())) {
            if(verifyStock(productStock, cartForm, request)) {
                cartForm.setAmount(cartForm.getAmount() + request.getAmount());
                if(cartForm.getAmount() > 0) {
                    return cartForm;
                } else {
                 throw new RuntimeException("Amount must be at least 1, please delete this product instead.");
                }
            } else {
                throw new RuntimeException("Insufficient Product Stock.");
            }
        }
        return cartForm;
    }

    private Boolean verifyStock(ProductStock productStock, CartForm cartForm, UpdateCartProductAmountRequest request) {
        return cartForm.getAmount() + request.getAmount() <= productStock.getProductStock();
    }

    private UpdateCartProductAmountResponse toResponse(Cart cart) {
        UpdateCartProductAmountResponse response = new UpdateCartProductAmountResponse();
        BeanUtils.copyProperties(cart, response);
        return response;
    }
}
