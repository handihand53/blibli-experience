package com.blibli.experience.enums;

public enum CartTag {

  CART("Cart"),
  WISHLIST("Wishlist");

  private String cartTag;

  CartTag(String cartTag) {
    this.cartTag = cartTag;
  }

  public String getCartTag() {
    return cartTag;
  }

}
