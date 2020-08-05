package com.blibli.experience.entity.dto;

import com.blibli.experience.enums.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private UUID orderId;
    private String orderTransactionId;
    private UserDto userDto;
    private List<CartDto> cartDtos;
    private DeliveryType deliveryType;

}
