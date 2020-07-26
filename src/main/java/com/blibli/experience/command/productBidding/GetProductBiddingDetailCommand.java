package com.blibli.experience.command.productBidding;

import com.blibli.experience.model.response.productBidding.GetProductBiddingDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetProductBiddingDetailCommand extends Command<UUID, GetProductBiddingDetailResponse> {
}
