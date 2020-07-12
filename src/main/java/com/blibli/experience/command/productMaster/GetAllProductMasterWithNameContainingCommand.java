package com.blibli.experience.command.productMaster;

import com.blibli.experience.model.response.productMaster.GetAllProductMasterWithNameContainingResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductMasterWithNameContainingCommand extends Command<String, List<GetAllProductMasterWithNameContainingResponse>> {
}
