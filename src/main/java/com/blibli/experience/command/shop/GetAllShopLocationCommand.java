package com.blibli.experience.command.shop;

import com.blibli.experience.model.response.shop.GetAllShopLocationResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllShopLocationCommand extends Command<Integer, List<GetAllShopLocationResponse>> {
}
