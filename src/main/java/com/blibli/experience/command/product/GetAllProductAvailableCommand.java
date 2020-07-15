package com.blibli.experience.command.product;

import com.blibli.experience.model.response.product.GetAllProductAvailableResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductAvailableCommand extends Command<Integer, List<GetAllProductAvailableResponse>> {
}
