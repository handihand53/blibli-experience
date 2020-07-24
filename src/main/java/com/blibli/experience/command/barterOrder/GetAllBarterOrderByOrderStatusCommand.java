package com.blibli.experience.command.barterOrder;

import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.model.response.barterOrder.GetAllBarterOrderByOrderStatusResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllBarterOrderByOrderStatusCommand extends Command<BarterOrderStatus, List<GetAllBarterOrderByOrderStatusResponse>> {
}
