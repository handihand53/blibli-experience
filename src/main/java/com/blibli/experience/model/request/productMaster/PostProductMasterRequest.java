package com.blibli.experience.model.request.productMaster;

import com.blibli.experience.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductMasterRequest {

    @NotBlank
    private String productName;

    @NotBlank
    private String productBrand;

    @NotBlank
    private String productBarcode;

    @NotBlank
    private String productDescription;

    @NotBlank
    private String productVolume;

    private Double productWeight;
    private ProductCategory productCategory;
    private List<MultipartFile> productImage;

}
