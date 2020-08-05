package com.blibli.experience.commandImpl.order;

import com.blibli.experience.command.order.PostOrderCommand;
import com.blibli.experience.entity.document.*;
import com.blibli.experience.entity.dto.CartDto;
import com.blibli.experience.entity.dto.ShopDto;
import com.blibli.experience.entity.dto.UserDto;
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
        ShopDto shopDto = getShopForm(request);
        return userRepository.findFirstByUserId(cart.getUserId())
                .map(user -> toOrder(user, cart, shopDto, request))
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
                if(!productStock.getShopDto().getShopId().equals(request.getShopId())) {
                    throw new RuntimeException("Sorry, only 1 merchant per order. We will improve this soon :)");
                }
            } else {
                throw new RuntimeException("Product stock not found.");
            }
        }
        return productStocks;
    }

    private ShopDto getShopForm(PostOrderRequest request) {
        ShopDto shopDto = new ShopDto();
        Shop shop = shopRepository.findFirstByShopId(request.getShopId()).block();
        if (shop != null) {
            BeanUtils.copyProperties(shop, shopDto);
            return shopDto;
        } else {
            throw new RuntimeException("Shop not found");
        }
    }

    private Order toOrder(User user, Cart cart, ShopDto shopDto, PostOrderRequest request) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', '9').build();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return Order.builder()
                .orderId(UUID.randomUUID())
                .orderTransactionId("bliblimart-" + generator.generate(8))
                .userDto(userDto)
                .shopDto(shopDto)
                .cartDtos(getAndUpdateCartFormList(cart, request))
                .deliveryType(request.getDeliveryType())
                .orderStatus(OrderStatus.WAITING_FOR_PAYMENT)
                .paymentId(UUID.randomUUID())
                .orderCreatedAt(LocalDateTime.now())
                .build();
    }

    private List<CartDto> getAndUpdateCartFormList(Cart cart, PostOrderRequest request) {
        List<CartDto> cartDtos = new ArrayList<>();
        for (UUID uuid : request.getProductStockIdList()) {
            boolean checkCartForm = false;
            for (CartDto cartDto : cart.getCartDtos()) {
                if (uuid.equals(cartDto.getStockDto().getStockId())) {
                    checkCartForm = true;
                    cartDtos.add(cartDto);
                }
            }
            if (!checkCartForm) {
                throw new RuntimeException("Product not found in cart.");
            }
        }
        cart.getCartDtos().removeAll(cartDtos);
        cartRepository.save(cart).subscribe();
        return cartDtos;
    }

    private void subtractProductStock(Order order, List<ProductStock> productStockList) {
        for (CartDto cartDto : order.getCartDtos()) {
            for (ProductStock productStock : productStockList) {
                if (cartDto.getStockDto().getStockId().equals(productStock.getStockId())) {
                    productStock.setProductStock(productStock.getProductStock() - cartDto.getAmount());
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
