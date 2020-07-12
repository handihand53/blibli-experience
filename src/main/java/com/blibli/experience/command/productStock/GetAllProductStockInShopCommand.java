package com.blibli.experience.command.productStock;

import com.blibli.experience.model.response.productStock.GetAllProductStockInShopResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllProductStockInShopCommand extends Command<UUID, List<GetAllProductStockInShopResponse>> {
}
