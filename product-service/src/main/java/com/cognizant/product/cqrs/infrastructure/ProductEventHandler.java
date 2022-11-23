package com.cognizant.product.cqrs.infrastructure;

import org.springframework.stereotype.Service;

import com.cognizant.product.cqrs.events.ProductAddEvent;
import com.cognizant.product.cqrs.events.ProductDeleteEvent;
import com.cognizant.product.exception.ProductNotFoundException;
import com.cognizant.product.service.ProductService;

@Service
public class ProductEventHandler implements EventHandler{

	private ProductService productService;
	
	ProductEventHandler(ProductService productService) {
		this.productService = productService;
	}
	
	@Override
	public void on(ProductAddEvent event) {
		productService.addProduct(event);
	}

	@Override
	public void on(ProductDeleteEvent event) {
		try {
			productService.deleteProduct(event.getProductId());
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
