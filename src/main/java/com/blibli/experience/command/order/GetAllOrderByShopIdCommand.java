package com.blibli.experience.command.order;

import com.blibli.experience.model.response.order.GetAllOrderByShopIdResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllOrderByShopIdCommand extends Command<UUID, List<GetAllOrderByShopIdResponse>> {
}
