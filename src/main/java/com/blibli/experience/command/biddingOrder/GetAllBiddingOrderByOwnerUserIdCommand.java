package com.blibli.experience.command.biddingOrder;

import com.blibli.experience.model.response.biddingOrder.GetAllBiddingOrderByOwnerUserIdResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllBiddingOrderByOwnerUserIdCommand extends Command<UUID, List<GetAllBiddingOrderByOwnerUserIdResponse>> {
}
