package com.cognizant.buyer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.buyer.model.Buyer;

/**
 * @author Arkit Das
 */
@Repository
public interface BuyerRepository  extends JpaRepository<Buyer, String>{

	public Optional<Buyer> findByEmail(String email);
}
