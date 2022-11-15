package com.cognizant.buyer.cqrs.infrastructure;

import org.springframework.stereotype.Service;

import com.cognizant.buyer.cqrs.events.BuyerAddEvent;
import com.cognizant.buyer.service.BuyerService;

@Service
public class BuyerEventHandler implements EventHandler{

	private BuyerService buyerService;
	
	BuyerEventHandler(BuyerService buyerService) {
		this.buyerService = buyerService;
	}
	
	@Override
	public void on(BuyerAddEvent event) {
		buyerService.addProduct(event);
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
