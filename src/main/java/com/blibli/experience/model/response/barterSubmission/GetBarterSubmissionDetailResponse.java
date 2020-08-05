package com.blibli.experience.model.response.barterSubmission;

import com.blibli.experience.entity.dto.ProductBarterDto;
import com.blibli.experience.entity.dto.UserDto;
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
public class GetBarterSubmissionDetailResponse {

    private UUID barterSubmissionId;
    private UserDto userData;
    private String barterSubmissionName;
    private String barterSubmissionBrand;
    private String barterSubmissionDescription;
    private Double barterSubmissionWeight;
    private String barterSubmissionVolume;
    private String barterSubmissionPackage;
    private ProductBarterCondition barterSubmissionCondition;
    private List<String> barterSubmissionImagePaths;
    private ProductBarterDto barterSubmissionTargetBarter;
    private LocalDateTime barterSubmissionCreatedAt;

}
