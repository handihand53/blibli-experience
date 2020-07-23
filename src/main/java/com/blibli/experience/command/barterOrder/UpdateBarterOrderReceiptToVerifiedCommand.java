package com.blibli.experience.command.barterOrder;

import com.blibli.experience.model.request.barterOrder.UpdateBarterOrderReceiptToVerifiedRequest;
import com.blibli.experience.model.response.barterOrder.UpdateBarterOrderReceiptToVerifiedResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface UpdateBarterOrderReceiptToVerifiedCommand extends Command<UpdateBarterOrderReceiptToVerifiedRequest, UpdateBarterOrderReceiptToVerifiedResponse> {
}
