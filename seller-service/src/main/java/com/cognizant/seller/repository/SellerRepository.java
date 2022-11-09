package com.cognizant.seller.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.seller.model.Seller;

/**
 * @author Arkit Das
 */
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
	
	Optional<Seller> findByEmail(String email);

}
