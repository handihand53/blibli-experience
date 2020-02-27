package com.blibli.experience.command.shop;

import com.blibli.experience.model.response.shop.GetShopDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetShopDetailCommand extends Command<UUID, GetShopDetailResponse> {
}
