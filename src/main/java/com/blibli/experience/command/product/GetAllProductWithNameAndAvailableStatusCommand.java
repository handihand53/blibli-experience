package com.blibli.experience.command.product;

import com.blibli.experience.model.response.product.GetAllProductWithNameAndAvailableStatusResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductWithNameAndAvailableStatusCommand extends Command<String, List<GetAllProductWithNameAndAvailableStatusResponse>> {
}
