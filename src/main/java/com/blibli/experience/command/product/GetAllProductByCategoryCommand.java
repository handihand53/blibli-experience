package com.blibli.experience.command.product;

import com.blibli.experience.model.request.product.GetAllProductByCategoryRequest;
import com.blibli.experience.model.response.product.GetAllProductByCategoryResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductByCategoryCommand extends Command<GetAllProductByCategoryRequest, List<GetAllProductByCategoryResponse>> {
}
