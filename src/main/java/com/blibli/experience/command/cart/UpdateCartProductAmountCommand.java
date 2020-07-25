package com.blibli.experience.command.cart;

import com.blibli.experience.model.request.cart.UpdateCartProductAmountRequest;
import com.blibli.experience.model.response.cart.UpdateCartProductAmountResponse;
import com.blibli.oss.command.Command;

public interface UpdateCartProductAmountCommand extends Command<UpdateCartProductAmountRequest, UpdateCartProductAmountResponse> {
}
