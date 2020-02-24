package com.blibli.experience.enums;

public enum ProductTag {

  BLIBLIMART("Bliblimart"),
  BARTER("Barter"),
  BIDDING("Bidding"),
  BARTER_OFFER("Barter Offer");

  private String productTag;

  ProductTag(String productTag) {
    this.productTag = productTag;
  }

  public String getProductTag() {
    return productTag;
  }

}
