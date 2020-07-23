package com.blibli.experience;

public class ApiPath {

  // SOME FIXED VARIABLES
  private static final String API = "/api";

  // AUTH ENDPOINTS
  public static final String AUTH = API + "/auth";
  public static final String REGISTER = AUTH + "/user";
  public static final String REGISTER_SHOP = AUTH + "/shop";
  public static final String REGISTER_ADMIN = AUTH + "/admin";
  public static final String LOGIN = AUTH + "/login";

  // USER ENDPOINTS
  public static final String USER = API + "/users";
  public static final String USER_UPDATE_PASSWORD = USER + "/update/password";

  // SHOP ENDPOINTS
  public static final String SHOP = API + "/shops";

  // PRODUCT ENDPOINTS
  public static final String PRODUCT = API + "/products";
  public static final String PRODUCT_BLIBLIMART = PRODUCT + "/bliblimart";
  public static final String PRODUCT_AVAILABLE = PRODUCT + "/available";
  public static final String PRODUCT_CATEGORY = PRODUCT + "/category";
  public static final String PRODUCT_WITH_BARCODE = PRODUCT + "/barcode";
  public static final String PRODUCT_SEARCH = PRODUCT + "/search";
  public static final String PRODUCTS_ALL = PRODUCT + "/getAll";
  public static final String PRODUCT_ENUMS = PRODUCT + "/enums";
  public static final String PRODUCT_CATEGORY_ENUM = PRODUCT_ENUMS + "/category";

  // CART ENDPOINTS
  public static final String CARTS = API + "/carts";

  // BARTER ENDPOINTS
  public static final String BARTER = API + "/barter";
  public static final String BARTER_AVAILABLE = BARTER + "/available";

  // BARTER SUBMISSION ENDPOINTS
  public static final String BARTER_SUBMISSION = API + "/barterSubmission";
  public static final String SUBMISSION_BY_USER = BARTER_SUBMISSION + "/user";
  public static final String SUBMISSION_BY_PRODUCT_BARTER = BARTER_SUBMISSION + "/productBarter";

  // BARTER ORDER ENDPOINTS
  public static final String BARTER_ORDER = API + "/barterOrder";
  public static final String BARTER_ORDER_RECEIPT_TO_WAREHOUSE = BARTER_ORDER + "/toWarehouse";
  public static final String BARTER_ORDER_RECEIPT_IN_CONSUMERS = BARTER_ORDER + "/inConsumers";

  // ADMIN ENDPOINTS
  public static final String ADMIN = API + "/admin";
  public static final String ADMIN_PRODUCT_MASTER = ADMIN + "/productMasters";
  public static final String ADMIN_BARTER_ORDER = ADMIN + "/barterOrder";
  public static final String ADMIN_BARTER_ORDER_RECEIPT_TO_VERIFIED = ADMIN_BARTER_ORDER + "/toVerified";
  public static final String ADMIN_BARTER_ORDER_RECEIPT_TO_CONSUMERS = ADMIN_BARTER_ORDER + "/toConsumers";

  // MERCHANT ENDPOINTS
  public static final String MERCHANT = API + "/merchant";
  public static final String MERCHANT_PRODUCT_STOCK = MERCHANT + "/productStocks";

}
