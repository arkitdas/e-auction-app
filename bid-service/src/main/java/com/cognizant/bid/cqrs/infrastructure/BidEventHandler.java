package com.cognizant.bid.cqrs.infrastructure;

import org.springframework.stereotype.Service;

import com.cognizant.bid.cqrs.events.BidAddEvent;
import com.cognizant.bid.cqrs.events.BidUpdateAmountdEvent;
import com.cognizant.bid.service.BidService;

@Service
public class BidEventHandler implements EventHandler{

	private BidService bidService;
	
	BidEventHandler(BidService bidService) {
		this.bidService = bidService;
	}

	public void on(BidAddEvent event) {
		try {
			bidService.placeBids(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void on(BidUpdateAmountdEvent event) {
		try {
			bidService.updateBidAmount(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
