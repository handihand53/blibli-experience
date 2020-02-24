package com.blibli.experience.enums;

public enum ShopTag {

  BLIBLIMART("Bliblimart"),
  BARTER("Barter");

  private String shopTag;

  ShopTag(String shopTag) {
    this.shopTag = shopTag;
  }

  public String getShopTag() {
    return shopTag;

  }
}
