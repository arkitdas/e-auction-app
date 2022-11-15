package com.cognizant.seller.cqrs.infrastructure;

import org.springframework.stereotype.Service;

import com.cognizant.seller.cqrs.events.SellerAddEvent;
import com.cognizant.seller.service.SellerService;

@Service
public class SellerEventHandler implements EventHandler{

	private SellerService sellerService;
	
	SellerEventHandler(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	
	@Override
	public void on(SellerAddEvent event) {
		sellerService.addProduct(event);
	}

	/*
	 * @Override public void on(SellerDeleteEvent event) { try {
	 * sellerService.deleteProduct(event.getProductId()); } catch
	 * (ProductNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

}
