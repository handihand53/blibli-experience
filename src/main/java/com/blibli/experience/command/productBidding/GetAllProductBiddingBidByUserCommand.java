package com.blibli.experience.command.productBidding;

import com.blibli.experience.model.response.productBidding.GetAllProductBiddingBidByUserResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllProductBiddingBidByUserCommand extends Command<UUID, List<GetAllProductBiddingBidByUserResponse>> {
}
