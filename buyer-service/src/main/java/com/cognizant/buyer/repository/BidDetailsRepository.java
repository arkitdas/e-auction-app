package com.cognizant.buyer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.buyer.model.BidDetails;

/**
 * @author Arkit Das
 */
@Repository
public interface BidDetailsRepository  extends JpaRepository<BidDetails, String> {

	public Optional<List<BidDetails>> findByProductId(String productId);
	
}
