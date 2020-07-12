package com.blibli.experience;

public class ApiPath {

  // SOME FIXED VARIABLES
  private static final String API = "/api";

  // AUTH ENDPOINTS
  public static final String AUTH = API + "/auth";
  public static final String REGISTER = AUTH + "/register";
  public static final String LOGIN = AUTH + "/login";
  public static final String REGISTER_SHOP = AUTH + "/shop/register";

  // USER ENDPOINTS
  public static final String USER = API + "/users";
  public static final String USER_UPDATE_PASSWORD = USER + "/update/password";

  // SHOP ENDPOINTS
  public static final String SHOP = API + "/shops";
  public static final String SHOP_BLIBLIMART = SHOP + "/bliblimart";


  // PRODUCT ENDPOINTS
  public static final String PRODUCT = API + "/products";
  public static final String PRODUCT_WITH_BARCODE = PRODUCT + "/barcode";
  public static final String PRODUCT_ENUMS = PRODUCT + "/enums";
  public static final String PRODUCT_CATEGORY_ENUM = PRODUCT_ENUMS + "/category";
  public static final String PRODUCT_SEARCH = PRODUCT + "/search";
  public static final String PRODUCTS_ALL = PRODUCT + "/getAll";

  // CART ENDPOINTS
  public static final String CARTS = API + "/carts";

  // ADMIN ENDPOINTS
  public static final String ADMIN = API + "/admin";
  public static final String ADMIN_PRODUCT_MASTER = ADMIN + "/productMasters";

  // MERCHANT ENDPOINTS
  public static final String MERCHANT = API + "/merchant";
  public static final String MERCHANT_PRODUCT_STOCK = MERCHANT + "/productStocks";

}
