package com.blibli.experience.command.biddingOrder;

import com.blibli.experience.model.response.biddingOrder.UpdateBiddingOrderToConfirmationResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface UpdateBiddingOrderToConfirmationCommand extends Command<UUID, UpdateBiddingOrderToConfirmationResponse> {
}
