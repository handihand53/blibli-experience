package com.blibli.experience.command.order;

import com.blibli.experience.model.request.order.PostOrderRequest;
import com.blibli.experience.model.response.order.PostOrderResponse;
import com.blibli.oss.command.Command;

public interface PostOrderCommand extends Command<PostOrderRequest, PostOrderResponse> {
}
