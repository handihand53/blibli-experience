package com.blibli.experience.command.product;

import com.blibli.experience.model.response.product.GetProductsAvailableResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetProductsAvailableCommand
    extends Command<Integer, List<GetProductsAvailableResponse>> {
}
