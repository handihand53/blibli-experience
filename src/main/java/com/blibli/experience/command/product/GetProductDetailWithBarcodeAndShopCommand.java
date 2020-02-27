package com.blibli.experience.command.product;

import com.blibli.experience.model.request.product.GetProductDetailWithBarcodeAndShopRequest;
import com.blibli.experience.model.response.product.GetProductDetailWithBarcodeAndShopResponse;
import com.blibli.oss.command.Command;

public interface GetProductDetailWithBarcodeAndShopCommand extends Command<GetProductDetailWithBarcodeAndShopRequest,
    GetProductDetailWithBarcodeAndShopResponse> {
}
