package com.blibli.experience.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {

    private UUID stockId;
    private ShopDto shopDto;
    private ProductDto productDto;
    private Integer productStock;
    private Integer productPrice;
    
}
