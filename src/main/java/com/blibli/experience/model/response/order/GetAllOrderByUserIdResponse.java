package com.blibli.experience.model.response.order;

import com.blibli.experience.entity.form.CartForm;
import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.entity.form.StockForm;
import com.blibli.experience.entity.form.UserDataForm;
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
public class GetAllOrderByUserIdResponse {

    private UUID orderId;
    private String orderTransactionId;
    private UserDataForm userDataForm;
    private ShopForm shopForm;
    private List<CartForm> cartForms;
    private DeliveryType deliveryType;
    private UUID paymentId;
    private OrderStatus orderStatus;
    private LocalDateTime orderCreatedAt;

}
