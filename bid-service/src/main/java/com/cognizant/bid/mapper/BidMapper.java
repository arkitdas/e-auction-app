package com.cognizant.bid.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.cognizant.bid.cqrs.commands.BidAddCommand;
import com.cognizant.bid.cqrs.events.BidAddEvent;
import com.cognizant.bid.model.BidDetails;
import com.cognizant.bid.payload.BidRequestInfo;
import com.cognizant.bid.payload.BidResponseInfo;



@Mapper(componentModel = "spring")
public interface BidMapper {

	public static BidMapper INSTANCE = Mappers.getMapper( BidMapper.class );
	
	BidDetails toBidDetails(BidAddEvent event);
	
	@Mappings({
        @Mapping(target = "buyer", ignore = true),
	})
	BidResponseInfo toBidResponseInfo(BidDetails bidDetails);
	
	BidAddCommand toBidAddCommand(BidRequestInfo bidRequestInfo);
}
