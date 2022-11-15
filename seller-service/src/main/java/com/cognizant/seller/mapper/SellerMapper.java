package com.cognizant.seller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.cognizant.seller.cqrs.commands.SellerAddCommand;
import com.cognizant.seller.cqrs.events.SellerAddEvent;
import com.cognizant.seller.model.Seller;
import com.cognizant.seller.payload.BidDetailResponseInfo;
import com.cognizant.seller.payload.BidDetailsResponseInfo;
import com.cognizant.seller.payload.ProductAddRequestInfo;
import com.cognizant.seller.payload.ProductResponseInfo;
import com.cognizant.seller.payload.ProductShortResponseInfo;
import com.cognizant.seller.payload.SellerProductResponseInfo;
import com.cognizant.seller.payload.SellerRequestInfo;
import com.cognizant.seller.payload.SellerResponseInfo;

@Mapper(componentModel = "spring")
public interface SellerMapper {

	public static SellerMapper INSTANCE = Mappers.getMapper( SellerMapper.class );
	
	BidDetailsResponseInfo toBidDetailsResponseInfo(BidDetailResponseInfo bidDetailResponseInfo);
	
	ProductShortResponseInfo toProductShortResponseInfo(ProductResponseInfo productResponseInfo);
	
	Seller toSeller(SellerRequestInfo sellerRequestInfo);
	
	Seller toSeller(SellerAddEvent sellerAddEvent);
	
	SellerResponseInfo toSellerResponseInfo(Seller seller);
	
	@Mappings({
        @Mapping(target = "firstName", source = "seller.firstName"),
        @Mapping(target = "lastName", source = "seller.lastName"),
        @Mapping(target = "address", source = "seller.address"),
        @Mapping(target = "city", source = "seller.city"),
        @Mapping(target = "state", source = "seller.state"),
        @Mapping(target = "pin", source = "seller.pin"),
        @Mapping(target = "phone", source = "seller.phone"),
        @Mapping(target = "email", source = "seller.email")
    })
	SellerAddCommand toSellerAddCommand(ProductAddRequestInfo productAddRequestInfo);
	
	@Mappings({
        @Mapping(target = "firstName", source = "seller.firstName"),
        @Mapping(target = "lastName", source = "seller.lastName"),
        @Mapping(target = "address", source = "seller.address"),
        @Mapping(target = "city", source = "seller.city"),
        @Mapping(target = "state", source = "seller.state"),
        @Mapping(target = "pin", source = "seller.pin"),
        @Mapping(target = "phone", source = "seller.phone"),
        @Mapping(target = "email", source = "seller.email")
    })
	SellerProductResponseInfo toSellerProductResponseInfo(Seller seller);
	
}
