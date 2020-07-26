package com.blibli.experience.command.productBidding;

import com.blibli.experience.model.response.productBidding.UpdateProductBiddingToCloseResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface UpdateProductBiddingToCloseCommand extends Command<Integer, List<UpdateProductBiddingToCloseResponse>> {
}
