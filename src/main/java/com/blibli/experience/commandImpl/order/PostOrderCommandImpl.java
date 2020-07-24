package com.blibli.experience.commandImpl.order;

import com.blibli.experience.command.order.PostOrderCommand;
import com.blibli.experience.entity.document.Cart;
import com.blibli.experience.entity.document.Order;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.entity.form.StockForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.OrderStatus;
import com.blibli.experience.model.request.order.PostOrderRequest;
import com.blibli.experience.model.response.order.PostOrderResponse;
import com.blibli.experience.repository.CartRepository;
import com.blibli.experience.repository.OrderRepository;
import com.blibli.experience.repository.UserRepository;
import javassist.NotFoundException;
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

    @Autowired
    public PostOrderCommandImpl(CartRepository cartRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<PostOrderResponse> execute(PostOrderRequest request) {
        Cart cart = getCart(request);
        return userRepository.findFirstByUserId(cart.getUserId())
                .map(user -> toOrder(user, cart, request))
                .flatMap(order -> orderRepository.save(order))
                .doOnNext(order -> flushStockForm(cart))
                .map(this::toResponse);
    }

    private Order toOrder(User user, Cart cart, PostOrderRequest request) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', '9').build();
        UserDataForm userDataForm = new UserDataForm();
        BeanUtils.copyProperties(user, userDataForm);
        return Order.builder()
                .orderId(UUID.randomUUID())
                .orderTransactionId("bliblimart-" + generator.generate(8))
                .userDataForm(userDataForm)
                .stockForms(cart.getStockForms())
                .deliveryType(request.getDeliveryType())
                .orderStatus(OrderStatus.WAITING_FOR_PAYMENT)
                .paymentId(UUID.randomUUID())
                .orderCreatedAt(LocalDateTime.now())
                .build();
    }

    private Cart getCart(PostOrderRequest request) {
        Cart cart = cartRepository.findFirstByCartId(request.getCartId()).block();
        if(cart != null) {
            return cart;
        } else {
            throw new RuntimeException("Cart not found.");
        }
    }

    private void flushStockForm(Cart cart) {
        List<StockForm> stockForms = new ArrayList<>();
        cart.setStockForms(stockForms);
        cartRepository.save(cart).subscribe();
    }

    private PostOrderResponse toResponse(Order order) {
        PostOrderResponse response = new PostOrderResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }
}
