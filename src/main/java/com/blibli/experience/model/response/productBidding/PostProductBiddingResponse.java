package com.blibli.experience.model.response.productBidding;

import com.blibli.experience.entity.dto.UserDto;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBiddingCondition;
import com.blibli.experience.enums.ProductCategory;
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
public class PostProductBiddingResponse {

    private UUID productBiddingId;
    private UserDto userData;
    private String productBiddingName;
    private String productBiddingBrand;
    private String productBiddingDescription;
    private Double productBiddingWeight;
    private String productBiddingVolume;
    private String productBiddingPackage;
    private ProductCategory productCategory;
    private Integer startPrice;
    private Integer nextBid;
    private ProductBiddingCondition productBiddingCondition;
    private List<String> productBiddingImagePaths;
    private ProductAvailableStatus availableStatus;
    private LocalDateTime closeBidDate;

}
