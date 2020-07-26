package com.blibli.experience.command.biddingOrder;

import com.blibli.experience.model.response.biddingOrder.GetBiddingOrderDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetBiddingOrderDetailCommand extends Command<UUID, GetBiddingOrderDetailResponse> {
}
