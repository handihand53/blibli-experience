package com.blibli.experience.command.barterOrder;

import com.blibli.experience.model.request.barterOrder.GetAllBarterOrderByUserIdRequest;
import com.blibli.experience.model.response.barterOrder.GetAllBarterOrderByUserIdResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllBarterOrderByUserIdCommand extends Command<GetAllBarterOrderByUserIdRequest, List<GetAllBarterOrderByUserIdResponse>> {
}
