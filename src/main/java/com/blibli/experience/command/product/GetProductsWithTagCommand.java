package com.blibli.experience.command.product;

import com.blibli.experience.enums.ProductTag;
import com.blibli.experience.model.response.product.GetProductsWithTagResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetProductsWithTagCommand extends Command<ProductTag, List<GetProductsWithTagResponse>> {
}
