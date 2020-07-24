package com.blibli.experience.command.order;

import com.blibli.experience.model.response.order.GetAllOrderByUserIdResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllOrderByUserIdCommand extends Command<UUID, List<GetAllOrderByUserIdResponse>> {
}
