package com.blibli.experience.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressForm {

  private String detail;
  private String rt;
  private String rw;
  private String kelurahan;
  private String kecamatan;
  private String kota;
  private String provinsi;

}
