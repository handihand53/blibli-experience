package com.blibli.experience.command.cart;

import com.blibli.experience.model.response.cart.GetCartWithUserIdResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetCartWithUserIdCommand extends Command<UUID, GetCartWithUserIdResponse> {
}
