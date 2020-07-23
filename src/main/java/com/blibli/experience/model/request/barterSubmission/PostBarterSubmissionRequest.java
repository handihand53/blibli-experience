package com.blibli.experience.model.request.barterSubmission;

import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.enums.ProductBarterCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostBarterSubmissionRequest {

    private UUID userId;
    private UUID productBarterId;
    private String barterSubmissionName;
    private String barterSubmissionBrand;
    private String barterSubmissionDescription;
    private Double barterSubmissionWeight;
    private String barterSubmissionVolume;
    private String barterSubmissionPackage;
    private ProductBarterCondition barterSubmissionCondition;
    private List<MultipartFile> barterSubmissionImages;


}
