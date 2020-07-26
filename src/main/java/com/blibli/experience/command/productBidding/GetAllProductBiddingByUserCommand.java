package com.blibli.experience.command.productBidding;

import com.blibli.experience.model.response.productBidding.GetAllProductBiddingByUserResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllProductBiddingByUserCommand extends Command<UUID, List<GetAllProductBiddingByUserResponse>> {
}
