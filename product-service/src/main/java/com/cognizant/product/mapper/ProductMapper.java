package com.cognizant.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.cognizant.product.model.Product;
import com.cognizant.product.payload.ProductInfo;
import com.cognizant.product.payload.ProductResponseInfo;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	public static ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );
	
	ProductInfo toProductInfo(Product product);
	
	Product toProduct(ProductInfo productInfo);
	
	@Mappings({
        @Mapping(target = "sellerId", source = "createdBy"),
	})
	ProductResponseInfo toProductResponseInfo(Product product);
	
}
