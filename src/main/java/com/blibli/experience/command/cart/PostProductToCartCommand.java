package com.blibli.experience.command.cart;

import com.blibli.experience.model.request.cart.PostProductToCartRequest;
import com.blibli.experience.model.response.cart.PostProductToCartResponse;
import com.blibli.oss.command.Command;

public interface PostProductToCartCommand extends Command<PostProductToCartRequest, PostProductToCartResponse> {
}
