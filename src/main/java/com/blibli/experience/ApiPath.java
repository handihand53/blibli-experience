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

  // PRODUCT ENDPOINTS
  public static final String PRODUCT = API + "/products";
  public static final String PRODUCT_AVAILABLE = PRODUCT + "/available";

}
