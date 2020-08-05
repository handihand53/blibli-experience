package com.blibli.experience.model.response.productStock;

import com.blibli.experience.entity.dto.ProductDto;
import com.blibli.experience.entity.dto.ShopDto;
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
    private ShopDto shopDto;
    private ProductDto productDto;
    private Integer productStock;
    private Integer productPrice;
    private LocalDateTime stockCreatedAt;

}
