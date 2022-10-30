package com.cognizant.seller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.cognizant.seller.model.Seller;
import com.cognizant.seller.payload.BidDetailResponseInfo;
import com.cognizant.seller.payload.BidDetailsResponseInfo;
import com.cognizant.seller.payload.ProductResponseInfo;
import com.cognizant.seller.payload.ProductShortResponseInfo;
import com.cognizant.seller.payload.SellerRequestInfo;
import com.cognizant.seller.payload.SellerResponseInfo;

@Mapper(componentModel = "spring")
public interface SellerMapper {

	public static SellerMapper INSTANCE = Mappers.getMapper( SellerMapper.class );
	
	BidDetailsResponseInfo toBidDetailsResponseInfo(BidDetailResponseInfo bidDetailResponseInfo);
	
	ProductShortResponseInfo toProductShortResponseInfo(ProductResponseInfo productResponseInfo);
	
	Seller toSeller(SellerRequestInfo sellerRequestInfo);
	
	SellerResponseInfo toSellerResponseInfo(Seller seller);
	
}
