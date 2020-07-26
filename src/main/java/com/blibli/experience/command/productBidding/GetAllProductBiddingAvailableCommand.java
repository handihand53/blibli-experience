package com.blibli.experience.command.productBidding;

import com.blibli.experience.model.response.productBidding.GetAllProductBiddingAvailableResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductBiddingAvailableCommand extends Command<Integer, List<GetAllProductBiddingAvailableResponse>> {
}
