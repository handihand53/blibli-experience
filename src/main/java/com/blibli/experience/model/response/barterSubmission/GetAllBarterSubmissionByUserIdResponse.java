package com.blibli.experience.model.response.barterSubmission;

import com.blibli.experience.entity.form.ProductBarterDataForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.ProductBarterCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllBarterSubmissionByUserIdResponse {

    private UUID barterSubmissionId;
    private UserDataForm userData;
    private String barterSubmissionName;
    private String barterSubmissionBrand;
    private String barterSubmissionDescription;
    private Double barterSubmissionWeight;
    private String barterSubmissionVolume;
    private String barterSubmissionPackage;
    private ProductBarterCondition barterSubmissionCondition;
    private List<String> barterSubmissionImagePaths;
    private ProductBarterDataForm barterSubmissionTargetBarter;
    private LocalDateTime barterSubmissionCreatedAt;

}
