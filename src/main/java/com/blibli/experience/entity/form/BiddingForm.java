package com.blibli.experience.entity.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BiddingForm {

    private UserDataForm userDataForm;
    private Integer biddingPrice;
    private LocalDateTime createdAt;

}
