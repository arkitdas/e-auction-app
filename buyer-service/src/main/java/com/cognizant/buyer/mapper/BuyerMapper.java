package com.cognizant.buyer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.cognizant.buyer.cqrs.commands.BidAddCommand;
import com.cognizant.buyer.model.BidDetails;
import com.cognizant.buyer.model.Buyer;
import com.cognizant.buyer.payload.BidDetailResponseInfo;
import com.cognizant.buyer.payload.BidDetailsInfo;
import com.cognizant.buyer.payload.BidDetailsResponseInfo;
import com.cognizant.buyer.payload.BidRequestInfo;
import com.cognizant.buyer.payload.BidResponseInfo;
import com.cognizant.buyer.payload.BuyerInfo;

@Mapper(componentModel = "spring")
public interface BuyerMapper {

	public static BuyerMapper INSTANCE = Mappers.getMapper( BuyerMapper.class );
	
	Buyer toBuyer(BuyerInfo buyerInfo);
	
	BidDetails toBidDetails(BidDetailsInfo bidDetailsInfo);
	
	@Mappings({
        @Mapping(target = "bidDetails", ignore = true),
	})
	BidResponseInfo toBidResponseInfo(Buyer buyer);
	
	BidDetailsResponseInfo toBidDetailsResponseInfo(BidDetails bidDetails);
	
	BidDetailResponseInfo toBidDetailResponseInfo(BidDetails bidDetails);
	
	BidAddCommand toBidAddCommand(BidRequestInfo bidRequestInfo);
	
}
