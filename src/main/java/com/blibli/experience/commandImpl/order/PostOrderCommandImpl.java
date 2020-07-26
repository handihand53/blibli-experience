package com.blibli.experience.commandImpl.order;

import com.blibli.experience.command.order.PostOrderCommand;
import com.blibli.experience.entity.document.*;
import com.blibli.experience.entity.form.CartForm;
import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.OrderStatus;
import com.blibli.experience.model.request.order.PostOrderRequest;
import com.blibli.experience.model.response.order.PostOrderResponse;
import com.blibli.experience.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
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
public class PostOrderCommandImpl implements PostOrderCommand {

    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductStockRepository productStockRepository;
    private ShopRepository shopRepository;

    @Autowired
    public PostOrderCommandImpl(CartRepository cartRepository, OrderRepository orderRepository, UserRepository userRepository, ProductStockRepository productStockRepository, ShopRepository shopRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productStockRepository = productStockRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public Mono<PostOrderResponse> execute(PostOrderRequest request) {
        Cart cart = getCart(request);
        List<ProductStock> productStocks = getProductStockList(request);
        ShopForm shopForm = getShopForm(request);
        return userRepository.findFirstByUserId(cart.getUserId())
                .map(user -> toOrder(user, cart, shopForm, request))
                .flatMap(order -> orderRepository.save(order))
                .doOnNext(order -> subtractProductStock(order, productStocks))
                .map(this::toResponse);
    }

    private Cart getCart(PostOrderRequest request) {
        Cart cart = cartRepository.findFirstByCartId(request.getCartId()).block();
        if (cart != null) {
            return cart;
        } else {
            throw new RuntimeException("Cart not found.");
        }
    }

    private List<ProductStock> getProductStockList(PostOrderRequest request) {
        List<ProductStock> productStocks = new ArrayList<>();
        for (UUID uuid : request.getProductStockIdList()) {
            ProductStock productStock = productStockRepository.findFirstByStockId(uuid).block();
            if (productStock != null) {
                productStocks.add(productStock);
                if(!productStock.getShopForm().getShopId().equals(request.getShopId())) {
                    throw new RuntimeException("Sorry, only 1 merchant per order. We will improve this soon :)");
                }
            } else {
                throw new RuntimeException("Product stock not found.");
            }
        }
        return productStocks;
    }

    private ShopForm getShopForm(PostOrderRequest request) {
        ShopForm shopForm = new ShopForm();
        Shop shop = shopRepository.findFirstByShopId(request.getShopId()).block();
        if (shop != null) {
            BeanUtils.copyProperties(shop, shopForm);
            return shopForm;
        } else {
            throw new RuntimeException("Shop not found");
        }
    }

    private Order toOrder(User user, Cart cart, ShopForm shopForm, PostOrderRequest request) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', '9').build();
        UserDataForm userDataForm = new UserDataForm();
        BeanUtils.copyProperties(user, userDataForm);
        return Order.builder()
                .orderId(UUID.randomUUID())
                .orderTransactionId("bliblimart-" + generator.generate(8))
                .userDataForm(userDataForm)
                .shopForm(shopForm)
                .cartForms(getAndUpdateCartFormList(cart, request))
                .deliveryType(request.getDeliveryType())
                .orderStatus(OrderStatus.WAITING_FOR_PAYMENT)
                .paymentId(UUID.randomUUID())
                .orderCreatedAt(LocalDateTime.now())
                .build();
    }

    private List<CartForm> getAndUpdateCartFormList(Cart cart, PostOrderRequest request) {
        List<CartForm> cartForms = new ArrayList<>();
        for (UUID uuid : request.getProductStockIdList()) {
            boolean checkCartForm = false;
            for (CartForm cartForm : cart.getCartForms()) {
                if (uuid.equals(cartForm.getStockForm().getStockId())) {
                    checkCartForm = true;
                    cartForms.add(cartForm);
                }
            }
            if (!checkCartForm) {
                throw new RuntimeException("Product not found in cart.");
            }
        }
        cart.getCartForms().removeAll(cartForms);
        cartRepository.save(cart).subscribe();
        return cartForms;
    }

    private void subtractProductStock(Order order, List<ProductStock> productStockList) {
        for (CartForm cartForm : order.getCartForms()) {
            for (ProductStock productStock : productStockList) {
                if (cartForm.getStockForm().getStockId().equals(productStock.getStockId())) {
                    productStock.setProductStock(productStock.getProductStock() - cartForm.getAmount());
                    productStockRepository.save(productStock).subscribe();
                }
            }
        }
    }

    private PostOrderResponse toResponse(Order order) {
        PostOrderResponse response = new PostOrderResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }
}
