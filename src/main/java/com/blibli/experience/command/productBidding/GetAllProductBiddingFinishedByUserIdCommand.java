package com.blibli.experience.command.productBidding;

import com.blibli.experience.model.response.productBidding.GetAllProductBiddingFinishedByUserIdResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllProductBiddingFinishedByUserIdCommand extends Command<UUID, List<GetAllProductBiddingFinishedByUserIdResponse>> {
}
