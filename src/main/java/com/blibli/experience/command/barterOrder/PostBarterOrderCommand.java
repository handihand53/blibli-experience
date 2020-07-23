package com.blibli.experience.command.barterOrder;

import com.blibli.experience.model.request.barterOrder.PostBarterOrderRequest;
import com.blibli.experience.model.response.barterOrder.PostBarterOrderResponse;
import com.blibli.experience.model.response.barterSubmission.PostBarterSubmissionResponse;
import com.blibli.oss.command.Command;

public interface PostBarterOrderCommand extends Command<PostBarterOrderRequest, PostBarterOrderResponse> {
}
