package com.blibli.experience.model.response.productStock;

import com.blibli.experience.entity.form.ProductDataForm;
import com.blibli.experience.entity.form.ShopForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductStockResponse {

    private UUID stockId;
    private ShopForm shopForm;
    private ProductDataForm productDataForm;
    private Integer productStock;
    private Integer productPrice;
    private LocalDateTime stockCreatedAt;

}
