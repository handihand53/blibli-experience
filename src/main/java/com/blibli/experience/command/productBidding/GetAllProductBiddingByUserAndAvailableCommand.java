package com.blibli.experience.command.productBidding;

import com.blibli.experience.model.response.productBidding.GetAllProductBiddingByUserAndAvailableResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllProductBiddingByUserAndAvailableCommand extends Command<UUID, List<GetAllProductBiddingByUserAndAvailableResponse>> {
}
