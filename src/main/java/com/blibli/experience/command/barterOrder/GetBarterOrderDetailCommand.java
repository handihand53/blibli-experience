package com.blibli.experience.command.barterOrder;

import com.blibli.experience.model.response.barterOrder.GetBarterOrderDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetBarterOrderDetailCommand extends Command<UUID, GetBarterOrderDetailResponse> {
}
