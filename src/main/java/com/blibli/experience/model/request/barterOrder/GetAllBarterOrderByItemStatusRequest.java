package com.blibli.experience.model.request.barterOrder;

import com.blibli.experience.enums.BarterItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllBarterOrderByItemStatusRequest {

    private BarterItemStatus barterItemStatus;

}
