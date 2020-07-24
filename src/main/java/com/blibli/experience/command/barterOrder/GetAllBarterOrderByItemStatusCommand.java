package com.blibli.experience.command.barterOrder;

import com.blibli.experience.model.request.barterOrder.GetAllBarterOrderByItemStatusRequest;
import com.blibli.experience.model.response.barterOrder.GetAllBarterOrderByItemStatusResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllBarterOrderByItemStatusCommand extends Command<GetAllBarterOrderByItemStatusRequest, List<GetAllBarterOrderByItemStatusResponse>> {
}
