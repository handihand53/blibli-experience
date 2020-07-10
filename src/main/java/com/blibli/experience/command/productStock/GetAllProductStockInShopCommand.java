package com.blibli.experience.command.productStock;

import com.blibli.experience.model.request.productStock.GetAllProductStockInShopRequest;
import com.blibli.experience.model.response.productStock.GetAllProductStockInShopResponse;
import com.blibli.oss.command.Command;
import reactor.core.publisher.Flux;

public interface GetAllProductStockInShopCommand extends Command<GetAllProductStockInShopRequest, Flux<GetAllProductStockInShopResponse>> {
}
