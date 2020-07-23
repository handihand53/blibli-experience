package com.blibli.experience.model.request.productBarter;

import com.blibli.experience.enums.ProductBarterCondition;
import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductBarterRequest {

    @NotBlank
    private String productBarterName;

    @NotNull
    private UUID userId;

    @NotBlank
    private String productBarterBrand;

    @NotBlank
    private String productBarterDescription;

    @NotBlank
    private String productBarterVolume;

    @NotBlank
    private String productBarterPreference;

    @NotBlank
    private String productBarterPackage;

    private List<String> productBarterImagePaths;
    private List<MultipartFile> productBarterImages;
    private Double productBarterWeight;
    private ProductCategory productCategory;
    private ProductBarterCondition productBarterCondition;

}
