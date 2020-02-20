package com.blibli.experience.enums;

public enum ProductTag {

  BLIBLIMART("Bliblimart"),
  BARTER("Barter"),
  BIDDING("Bidding");

  private String productTag;

  ProductTag(String productTag) {
    this.productTag = productTag;
  }

  public String getProductTag() {
    return productTag;
  }

}
