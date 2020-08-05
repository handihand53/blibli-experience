package com.blibli.experience.model.response.order;

import com.blibli.experience.entity.dto.CartDto;
import com.blibli.experience.entity.dto.ShopDto;
import com.blibli.experience.entity.dto.UserDto;
import com.blibli.experience.enums.DeliveryType;
import com.blibli.experience.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderByShopIdResponse {

    private UUID orderId;
    private String orderTransactionId;
    private UserDto userDto;
    private ShopDto shopDto;
    private List<CartDto> cartDtos;
    private DeliveryType deliveryType;
    private UUID paymentId;
    private OrderStatus orderStatus;
    private LocalDateTime orderCreatedAt;

}
