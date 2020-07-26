package com.blibli.experience.command.biddingOrder;

import com.blibli.experience.model.response.biddingOrder.GetAllBiddingOrderByWinnerUserIdResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllBiddingOrderByWinnerUserIdCommand extends Command<UUID, List<GetAllBiddingOrderByWinnerUserIdResponse>> {
}
