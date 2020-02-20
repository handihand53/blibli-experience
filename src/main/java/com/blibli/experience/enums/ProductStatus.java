package com.blibli.experience.enums;

public enum ProductStatus {

  NEW("New"),
  SECOND("Second");

  private String productStatus;

  ProductStatus(String productStatus) {
    this.productStatus = productStatus;
  }

  public String getProductStatus() {
    return productStatus;
  }

}
