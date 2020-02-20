package com.blibli.experience.enums;

public enum GenderType {

  PRIA("Pria"),
  WANITA("Wanita");

  private String genderType;

  GenderType(String genderType) {
    this.genderType = genderType;
  }

  public String getGenderType() {
    return genderType;
  }

}
