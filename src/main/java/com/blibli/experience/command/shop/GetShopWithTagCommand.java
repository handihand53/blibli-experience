package com.blibli.experience.command.shop;

import com.blibli.experience.enums.ShopTag;
import com.blibli.experience.model.response.shop.GetShopWithTagResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetShopWithTagCommand extends Command<ShopTag, List<GetShopWithTagResponse>> {
}
