package com.blibli.experience.model.request.productStock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductStockRequest {

    @NotNull
    private UUID stockId;

    @NotNull
    private Integer addedStock;

}
