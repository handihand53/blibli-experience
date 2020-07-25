package com.blibli.experience.model.response.cart;

import com.blibli.experience.entity.form.CartForm;
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
public class DeleteProductInCartResponse {

    private UUID cartId;
    private UUID userId;
    private List<CartForm> cartForms;
    private LocalDateTime lastUpdated;

}
