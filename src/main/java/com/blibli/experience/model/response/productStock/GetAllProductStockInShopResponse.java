package com.blibli.experience.model.response.productStock;

import com.blibli.experience.entity.form.ProductForm;
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
public class GetAllProductStockInShopResponse {

    private UUID stockId;
    private ProductForm productForm;
    private Integer productStock;
    private Integer productPrice;
    private LocalDateTime stockCreatedAt;

}
