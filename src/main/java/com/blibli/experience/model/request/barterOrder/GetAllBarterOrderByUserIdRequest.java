package com.blibli.experience.model.request.barterOrder;

import com.blibli.experience.enums.BarterRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllBarterOrderByUserIdRequest {

    @NotNull
    private UUID userId;

    private BarterRoleEnum barterRoleEnum;

}
