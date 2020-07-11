package com.blibli.experience.entity.document;

import com.blibli.experience.enums.GenderType;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.entity.form.AddressForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = User.COLLECTION_NAME)
public class User {

  public static final String COLLECTION_NAME = "user";
  public static final String ID = "id";
  public static final String EMAIL = "email";
  public static final String PASSWORD = "password";
  public static final String USER_NAME = "userName";
  public static final String ADDRESS = "address";
  public static final String PHONE_NUMBER = "phoneNumber";
  public static final String GENDER = "gender";
  public static final String BIRTH_DATE = "birthDate";
  public static final String IDENTITY_ID = "identityId";
  public static final String ROLE = "role";
  public static final String CREATED_AT = "createdAt";

  @Id
  @Field(value = ID)
  private UUID userId;

  @Field(value = EMAIL)
  @Indexed(unique = true)
  @Length(max = 254)
  private String userEmail;

  @Field(value = PASSWORD)
  private String userPassword;

  @Field(value = USER_NAME)
  @Length(max = 30)
  private String userName;

  @Field(value = ADDRESS)
  private List<AddressForm> userAddressForms;

  @Field(value = BIRTH_DATE)
  private LocalDate userBirthDate;

  @Field(value = PHONE_NUMBER)
  @Length(max = 15)
  private String userPhoneNumber;

  @Field(value = GENDER)
  private GenderType userGender;

  @Field(value = IDENTITY_ID)
  @Length(max = 16)
  private String userIdentityId;

  @Field(value = ROLE)
  private List<UserRole> userRoles;

  @Field(value = CREATED_AT)
  private LocalDateTime userCreatedAt;

}
