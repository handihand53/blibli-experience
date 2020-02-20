package com.blibli.experience.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantForm {

  private UUID id;
  private String email;
  private String fullName;
  private String phoneNumber;
  private List<AddressForm> addressForms;

}
