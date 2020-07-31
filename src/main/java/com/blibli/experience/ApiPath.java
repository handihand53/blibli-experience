package com.blibli.experience;

public class ApiPath {

  // SOME FIXED VARIABLES
  private static final String API = "/api";
  private static final String EXCLUSION = API + "/exc";

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
  public static final String PRODUCT_SEARCH_AVAILABLE = PRODUCT + "/search" + "/inStock";
  public static final String PRODUCT_SEARCH = PRODUCT + "/search";
  public static final String PRODUCTS_ALL = PRODUCT + "/getAll";
  public static final String PRODUCT_ENUMS = PRODUCT + "/enums";
  public static final String PRODUCT_CATEGORY_ENUM = PRODUCT_ENUMS + "/category";

  // CART ENDPOINTS
  public static final String CARTS = API + "/carts";
  public static final String CARTS_UPDATE_AMOUNT = CARTS + "/updateAmount";
  public static final String CARTS_DELETE_PRODUCT = CARTS + "/deleteProduct";

  // ORDER ENDPOINTS
  public static final String ORDER = API + "/order";
  public static final String ORDER_BY_USER = ORDER + "/user";

  // ORDER EXCEPTION ENDPOINTS
  private static final String ORDER_EXCLUSION = EXCLUSION + "/order";
  public static final String ORDER_TO_FINISHED = ORDER_EXCLUSION + "/toFinished";

  // PAYMENT ENDPOINTS
  public static final String PAYMENT = API + "/payment";

  // PRODUCT BARTER ENDPOINTS
  public static final String PRODUCT_BARTER = API + "/barter";
  public static final String PRODUCT_BARTER_AVAILABLE = PRODUCT_BARTER + "/available";
  public static final String PRODUCT_BARTER_BY_CATEGORY = PRODUCT_BARTER + "/category";
  public static final String PRODUCT_BARTER_BY_USER = PRODUCT_BARTER + "/user";

  // BARTER SUBMISSION ENDPOINTS
  public static final String BARTER_SUBMISSION = API + "/barterSubmission";
  public static final String SUBMISSION_BY_USER = BARTER_SUBMISSION + "/user";
  public static final String SUBMISSION_BY_PRODUCT_BARTER = BARTER_SUBMISSION + "/productBarter";

  // BARTER ORDER ENDPOINTS
  public static final String BARTER_ORDER = API + "/barterOrder";
  public static final String BARTER_ORDER_BY_USER = BARTER_ORDER + "/user";
  public static final String BARTER_ORDER_RECEIPT_TO_WAREHOUSE = BARTER_ORDER + "/toWarehouse";
  public static final String BARTER_ORDER_RECEIPT_IN_CONSUMERS = BARTER_ORDER + "/inConsumers";

  // PRODUCT BIDDING ENDPOINTS
  public static final String PRODUCT_BIDDING = PRODUCT + "/bidding";
  public static final String PRODUCT_BIDDING_TO_BID = PRODUCT_BIDDING + "/bid";
  public static final String PRODUCT_BIDDING_AVAILABLE = PRODUCT_BIDDING + "/available";
  public static final String PRODUCT_BIDDING_BY_CATEGORY = PRODUCT_BIDDING + "/category";
  public static final String PRODUCT_BIDDING_BIDDING_FORM_BY_USER = PRODUCT_BIDDING + "/user" + "/bidding";
  public static final String PRODUCT_BIDDING_BY_USER = PRODUCT_BIDDING + "/user";
  public static final String PRODUCT_BIDDING_BY_USER_FINISHED = PRODUCT_BIDDING + "/user" + "/finished";

  // BIDDING ORDER ENDPOINTS
  public static final String BIDDING_ORDER = API + "/biddingOrder";
  public static final String BIDDING_ORDER_BY_OWNER = BIDDING_ORDER + "/owner";
  public static final String BIDDING_ORDER_BY_WINNER = BIDDING_ORDER + "/winner";
  public static final String BIDDING_ORDER_DELIVERY_RECEIPT = BIDDING_ORDER + "/deliveryReceipt";

  // BIDDING ORDER EXCEPTION ENDPOINTS
  private static final String BIDDING_ORDER_EXCLUSION = EXCLUSION + "/biddingOrder";
  public static final String BIDDING_ORDER_CONFIRMATION = BIDDING_ORDER_EXCLUSION + "/confirmation";

  // BIDDING PAYMENT ENDPOINTS
  public static final String BIDDING_PAYMENT = API + "/biddingPayment";

  // ADMIN ENDPOINTS
  public static final String ADMIN = API + "/admin";
  public static final String ADMIN_PRODUCT_MASTER = ADMIN + "/productMasters";
  public static final String ADMIN_GENERATE_PRODUCT_MASTER_QR = ADMIN_PRODUCT_MASTER + "/generateQR";
  public static final String ADMIN_SYNCHRONIZE_PRODUCT_DATA_FORM_IN_STOCK = ADMIN + "/synchronizeDataForm";
  public static final String ADMIN_BARTER_ORDER = ADMIN + "/barterOrder";
  public static final String ADMIN_BARTER_ORDER_BY_ORDER_STATUS = ADMIN_BARTER_ORDER + "/orderStatus";
  public static final String ADMIN_BARTER_ORDER_BY_ITEM_STATUS = ADMIN_BARTER_ORDER + "/itemStatus";
  public static final String ADMIN_BARTER_ORDER_RECEIPT_TO_VERIFIED = ADMIN_BARTER_ORDER + "/toVerified";
  public static final String ADMIN_BARTER_ORDER_RECEIPT_TO_CONSUMERS = ADMIN_BARTER_ORDER + "/toConsumers";
  public static final String ADMIN_PRODUCT_BIDDING = ADMIN + "/productBidding";
  public static final String ADMIN_PRODUCT_BIDDING_TO_CLOSE = ADMIN_PRODUCT_BIDDING + "/close";

  // MERCHANT ENDPOINTS
  public static final String MERCHANT = API + "/merchant";
  public static final String MERCHANT_PRODUCT_STOCK = MERCHANT + "/productStocks";
  public static final String MERCHANT_ORDER = MERCHANT + "/order";
  public static final String MERCHANT_ORDER_DELIVERY_RECEIPT = MERCHANT_ORDER + "/deliveryReceipt";

  // METADATA ENDPOINTS
  public static final String METADATA = API + "/metadata";
  public static final String METADATA_PRODUCT_MASTER = METADATA + "/productMaster";
  public static final String METADATA_PRODUCT_BARTER = METADATA + "/productBarter";
  public static final String METADATA_PRODUCT_BIDDING = METADATA + "/productBidding";

}
