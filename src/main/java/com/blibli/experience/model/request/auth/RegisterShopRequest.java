package com.blibli.experience.model.request.auth;

import com.blibli.experience.entity.form.AddressForm;
import com.blibli.experience.enums.ShopTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterShopRequest {

  @NotBlank
  private String userEmail;

  @NotBlank
  @Length(max = 254)
  private String userPassword;

  @NotBlank
  private String userName;

  @NotBlank
  private String userIdentityId;

  @NotBlank
  private String userPhoneNumber;

  @NotBlank
  @Length(max = 50)
  private String shopName;

  @NotBlank
  private String shopDescription;

  @NotNull
  private AddressForm shopAddress;

  private List<ShopTag> shopTags;

  private Double[] shopLocation;


}
