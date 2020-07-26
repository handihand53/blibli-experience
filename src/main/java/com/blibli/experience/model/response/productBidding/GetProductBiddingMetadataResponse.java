package com.blibli.experience.model.response.productBidding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductBiddingMetadataResponse {

    private String userId = "UUID";
    private String productBiddingName = "String";
    private String productBiddingBrand = "String";
    private String productBiddingDescription = "String";
    private String productBiddingVolume = "String";
    private String productBiddingPackage = "String";
    private String startPrice = "Integer";
    private String nextBid = "Integer";
    private String productBiddingWeight = "Double";
    private String productCategory = "ProductCategory Enum";
    private String productBiddingCondition = "ProductBiddingCondition Enum";
    private String closeBidDate = "LocalDateTime";

}
