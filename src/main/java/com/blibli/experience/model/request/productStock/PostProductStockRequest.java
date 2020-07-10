package com.blibli.experience.model.request.productStock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductStockRequest {

    @NotBlank
    private UUID shopId;

    @NotBlank
    private UUID productId;

    @NotBlank
    private Integer productStock;

    @NotBlank
    private Integer productPrice;

}
