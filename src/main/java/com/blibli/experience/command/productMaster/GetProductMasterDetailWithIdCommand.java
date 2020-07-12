package com.blibli.experience.command.productMaster;

import com.blibli.experience.model.response.product.GetProductDetailWithIdAndShopIdResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetProductMasterDetailWithIdCommand extends Command<UUID, GetProductDetailWithIdAndShopIdResponse> {
}
