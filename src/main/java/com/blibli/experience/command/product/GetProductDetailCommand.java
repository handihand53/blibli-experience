package com.blibli.experience.command.product;

import com.blibli.experience.model.response.product.GetProductDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetProductDetailCommand extends Command<UUID, GetProductDetailResponse> {
}
