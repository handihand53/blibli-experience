package com.blibli.experience.command.productBidding;

import com.blibli.experience.model.request.productBidding.GetAllProductBiddingByAvailableAndCategoryRequest;
import com.blibli.experience.model.response.productBidding.GetAllProductBiddingByAvailableAndCategoryResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductBiddingByAvailableAndCategoryCommand extends Command<GetAllProductBiddingByAvailableAndCategoryRequest, List<GetAllProductBiddingByAvailableAndCategoryResponse>> {
}
