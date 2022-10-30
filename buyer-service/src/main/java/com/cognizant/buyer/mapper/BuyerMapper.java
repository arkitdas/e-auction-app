package com.cognizant.buyer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.cognizant.buyer.model.BidDetails;
import com.cognizant.buyer.model.Buyer;
import com.cognizant.buyer.payload.BidDetailResponseInfo;
import com.cognizant.buyer.payload.BidDetailsResponseInfo;
import com.cognizant.buyer.payload.BidRequestInfo;
import com.cognizant.buyer.payload.BidResponseInfo;

@Mapper(componentModel = "spring")
public interface BuyerMapper {

	public static BuyerMapper INSTANCE = Mappers.getMapper( BuyerMapper.class );
	
	/*
	 * ProductInfo toProductInfo(Product product);
	 * 
	 * Product toProduct(ProductInfo productInfo);
	 * 
	 * ProductResponseInfo toProductResponseInfo(Product product);
	 */
	
	@Mappings({
        @Mapping(target = "bidDetails", ignore = true),
	})
	Buyer toBuyer(BidRequestInfo bidRequestInfo);
	
	@Mappings({
        @Mapping(target = "buyer", ignore = true),
	})
	BidDetails toBidDetails(BidRequestInfo bidRequestInfo);
	
	@Mappings({
        @Mapping(target = "bidDetails", ignore = true),
	})
	BidResponseInfo toBidResponseInfo(Buyer buyer);
	
	BidDetailsResponseInfo toBidDetailsResponseInfo(BidDetails bidDetails);
	
	BidDetailResponseInfo toBidDetailResponseInfo(BidDetails bidDetails);
	
}
