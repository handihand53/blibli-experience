package com.blibli.experience.command.product;

import com.blibli.experience.model.response.product.GetProductDetailWithIdResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetProductDetailWithIdAndShopIdCommand extends Command<UUID, GetProductDetailWithIdResponse> {
}
