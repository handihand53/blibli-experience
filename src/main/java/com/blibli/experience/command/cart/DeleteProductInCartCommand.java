package com.blibli.experience.command.cart;

import com.blibli.experience.model.request.cart.DeleteProductInCartRequest;
import com.blibli.experience.model.response.cart.DeleteProductInCartResponse;
import com.blibli.oss.command.Command;

public interface DeleteProductInCartCommand extends Command<DeleteProductInCartRequest, DeleteProductInCartResponse> {
}
