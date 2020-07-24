package com.blibli.experience.entity.form;

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
public class OrderDataForm {

    private UUID orderId;
    private String orderTransactionId;
    private UserDataForm userDataForm;
    private List<StockForm> stockForms;
    private DeliveryType deliveryType;


}
