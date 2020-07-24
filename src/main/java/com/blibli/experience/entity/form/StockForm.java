package com.blibli.experience.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockForm {

    private UUID stockId;
    private ShopForm shopForm;
    private ProductDataForm productDataForm;
    private Integer productStock;
    private Integer productPrice;


}
