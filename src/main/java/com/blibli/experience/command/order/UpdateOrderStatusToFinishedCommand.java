package com.blibli.experience.command.order;

import com.blibli.experience.model.response.order.UpdateOrderStatusToFinishedResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface UpdateOrderStatusToFinishedCommand extends Command<UUID, UpdateOrderStatusToFinishedResponse> {
}
