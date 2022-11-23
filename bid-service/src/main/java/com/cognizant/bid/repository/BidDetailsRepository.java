package com.cognizant.bid.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bid.model.BidDetails;

/**
 * @author Arkit Das
 */
@Repository
public interface BidDetailsRepository  extends JpaRepository<BidDetails, String> {

	public Optional<List<BidDetails>> findByProductId(String productId);
	
	public Optional<List<BidDetails>> findByBuyerId(String buyerId);
	
	public Optional<BidDetails> findByProductIdAndBuyerId(String productId, String buyerId);
	
}
