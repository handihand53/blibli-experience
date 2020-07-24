package com.blibli.experience.command.order;

import com.blibli.experience.model.response.order.GetOrderDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetOrderDetailCommand extends Command<UUID, GetOrderDetailResponse> {
}
