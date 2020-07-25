package com.blibli.experience.commandImpl.cart;

import com.blibli.experience.command.cart.PostProductToCartCommand;
import com.blibli.experience.entity.document.Cart;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.entity.form.CartForm;
import com.blibli.experience.entity.form.StockForm;
import com.blibli.experience.model.request.cart.PostProductToCartRequest;
import com.blibli.experience.model.response.cart.PostProductToCartResponse;
import com.blibli.experience.repository.CartRepository;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.repository.ProductStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostProductToCartCommandImpl implements PostProductToCartCommand {

    private CartRepository cartRepository;
    private ProductMasterRepository productMasterRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public PostProductToCartCommandImpl(CartRepository cartRepository,
                                        ProductMasterRepository productMasterRepository,
                                        ProductStockRepository productStockRepository) {
        this.cartRepository = cartRepository;
        this.productMasterRepository = productMasterRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<PostProductToCartResponse> execute(PostProductToCartRequest request) {
        ProductStock productStock = getProductStock(request.getStockId());
        ProductMaster productMaster = getProductMaster(productStock.getProductDataForm().getProductId());
        return cartRepository.findFirstByUserId(request.getUserId())
                .switchIfEmpty(createCart(request))
                .map(cart -> addCartStockForm(cart, productMaster, productStock))
                .flatMap(cart -> cartRepository.save(cart))
                .map(this::toResponse);
    }

    private Mono<Cart> createCart(PostProductToCartRequest request) {
        log.info("#PostProductToCartCommand - Create new cart for user {}", request.getUserId());
        return Mono.fromCallable(() -> toCart(request))
                .flatMap(cart -> cartRepository.save(cart));
    }

    private Cart toCart(PostProductToCartRequest request) {
        List<CartForm> cartForms = new ArrayList<>();
        return Cart.builder()
                .cartId(UUID.randomUUID())
                .userId(request.getUserId())
                .cartForms(cartForms)
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    private Cart addCartStockForm(Cart cart, ProductMaster productMaster, ProductStock productStock) {
        List<CartForm> cartForms = cart.getCartForms();
        StockForm stockForm = new StockForm();
        BeanUtils.copyProperties(productStock, stockForm);
        BeanUtils.copyProperties(productMaster, stockForm.getProductDataForm());
        CartForm cartForm = CartForm.builder()
                .amount(1)
                .stockForm(stockForm)
                .build();
        cartForms.add(cartForm);
        cart.setCartForms(cartForms);
        cart.setLastUpdated(LocalDateTime.now());
        return cart;
    }

    private ProductStock getProductStock (UUID stockId) {
        ProductStock productStock = productStockRepository.findFirstByStockId(stockId).block();
        if (productStock != null) {
            return productStock;
        }
        else {
            throw new RuntimeException("Stock not found.");
        }
    }

    private ProductMaster getProductMaster (UUID productId) {
        ProductMaster productMaster = productMasterRepository.findFirstByProductId(productId).block();
        if (productMaster != null) {
            return productMaster;
        }
        else {
            throw new RuntimeException("Product Master not found.");
        }
    }

    private PostProductToCartResponse toResponse(Cart cart) {
        PostProductToCartResponse response = new PostProductToCartResponse();
        BeanUtils.copyProperties(cart, response);
        return response;
    }

}
