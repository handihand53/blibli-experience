package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.product.GetProductCategoryEnumCommand;
import com.blibli.experience.command.product.GetProductDetailWithBarcodeAndShopCommand;
import com.blibli.experience.command.product.GetProductDetailWithIdAndShopIdCommand;
import com.blibli.experience.command.product.GetProductTagEnumCommand;
import com.blibli.experience.command.product.GetProductsAvailableCommand;
import com.blibli.experience.command.product.GetProductsWithTagCommand;
import com.blibli.experience.enums.ProductTag;
import com.blibli.experience.model.request.product.GetProductDetailWithBarcodeAndShopRequest;
import com.blibli.experience.model.response.product.GetProductCategoryEnumResponse;
import com.blibli.experience.model.response.product.GetProductDetailWithBarcodeAndShopResponse;
import com.blibli.experience.model.response.product.GetProductDetailWithIdResponse;
import com.blibli.experience.model.response.product.GetProductTagEnumResponse;
import com.blibli.experience.model.response.product.GetProductsAvailableResponse;
import com.blibli.experience.model.response.product.GetProductsWithTagResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

  private CommandExecutor commandExecutor;

  @Autowired
  public ProductController(CommandExecutor commandExecutor) {
    this.commandExecutor = commandExecutor;
  }

  @GetMapping(value = ApiPath.PRODUCT)
  public Mono<Response<GetProductDetailWithIdResponse>> getDetailProductWithId(@RequestParam UUID id) {
    return commandExecutor.execute(GetProductDetailWithIdAndShopIdCommand.class, id)
        .log("#getDetailProductWithId - Successfully executing command.")
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @GetMapping(value = ApiPath.PRODUCT_WITH_BARCODE)
  public Mono<Response<GetProductDetailWithBarcodeAndShopResponse>> getDetailProductWithBarcodeAndShop(@ModelAttribute
      GetProductDetailWithBarcodeAndShopRequest request) {
    return commandExecutor.execute(GetProductDetailWithBarcodeAndShopCommand.class, request)
        .log("#getDetailProductWithBarcode - Successfully executing command.")
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @GetMapping(value = ApiPath.PRODUCTS_WITH_TAG)
  public Mono<Response<List<GetProductsWithTagResponse>>> getProductWithTag(@RequestParam ProductTag tag) {
    return commandExecutor.execute(GetProductsWithTagCommand.class, tag)
        .log("#getProductWithTag - Successfully executing command.")
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @GetMapping(value = ApiPath.PRODUCTS_AVAILABLE)
  public Mono<Response<List<GetProductsAvailableResponse>>> getAvailableProducts(@RequestParam Integer count) {
    return commandExecutor.execute(GetProductsAvailableCommand.class, count)
        .log("#getAvailableProducts - Successfully executing command.")
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @GetMapping(value = ApiPath.PRODUCT_CATEGORY_ENUM)
  public Mono<Response<GetProductCategoryEnumResponse>> getProductCategoryEnum() {
    return commandExecutor.execute(GetProductCategoryEnumCommand.class, "")
        .log("#getProductCategoryEnum - Successfully executing command.")
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

  @GetMapping(value = ApiPath.PRODUCT_TAG_ENUM)
  public Mono<Response<GetProductTagEnumResponse>> getProductTagEnum() {
    return commandExecutor.execute(GetProductTagEnumCommand.class, "")
        .log("#getProductTagEnum - Successfully executing command.")
        .map(ResponseHelper::ok)
        .subscribeOn(Schedulers.elastic());
  }

}
