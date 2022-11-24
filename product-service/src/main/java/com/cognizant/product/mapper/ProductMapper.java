package com.cognizant.product.mapper;

import javax.validation.Valid;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.cognizant.product.cqrs.commands.ProductAddCommand;
import com.cognizant.product.cqrs.events.ProductAddEvent;
import com.cognizant.product.model.Product;
import com.cognizant.product.payload.ProductAddRequestInfo;
import com.cognizant.product.payload.ProductInfo;
import com.cognizant.product.payload.ProductResponseInfo;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	public static ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );
	
	ProductInfo toProductInfo(Product product);
	
	Product toProduct(ProductInfo productInfo);
	
	Product toProduct(ProductAddEvent productAddEvent);
	
	ProductAddEvent toProductAddEvent(ProductInfo productInfo);
	
	@Mappings({
		@Mapping(target = "productName", source = "product.productName"),
        @Mapping(target = "shortDescription", source = "product.shortDescription"),
        @Mapping(target = "detailedDescription", source = "product.detailedDescription"),
        @Mapping(target = "categopry", source = "product.categopry"),
        @Mapping(target = "startingPrice", source = "product.startingPrice"),
        @Mapping(target = "bidEndDate", source = "product.bidEndDate"),
    })
	ProductAddCommand toProductAddCommand(ProductAddRequestInfo productAddRequestInfo);
	
	@Mappings({
        @Mapping(target = "sellerId", source = "createdBy"),
	})
	ProductResponseInfo toProductResponseInfo(Product product);
	
}
