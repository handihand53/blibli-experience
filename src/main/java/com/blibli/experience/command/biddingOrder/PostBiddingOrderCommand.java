package com.blibli.experience.command.biddingOrder;

import com.blibli.experience.model.response.biddingOrder.PostBiddingOrderResponse;
import com.blibli.experience.model.request.biddingOrder.PostBiddingOrderRequest;
import com.blibli.oss.command.Command;

public interface PostBiddingOrderCommand extends Command<PostBiddingOrderRequest, PostBiddingOrderResponse> {
}
