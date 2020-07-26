package com.blibli.experience.model.request.productBidding;

import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBiddingCondition;
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
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostProductBiddingRequest {

    @NotNull
    private UUID userId;

    @NotBlank
    private String productBiddingName;

    @NotBlank
    private String productBiddingBrand;

    @NotBlank
    private String productBiddingDescription;

    @NotBlank
    private String productBiddingVolume;

    @NotBlank
    private String productBiddingPackage;

    private Integer startPrice;
    private Integer nextBid;
    private Double productBiddingWeight;
    private ProductCategory productCategory;
    private ProductBiddingCondition productBiddingCondition;
    private List<MultipartFile> productImages;
    private LocalDateTime closeBidDate;

}
