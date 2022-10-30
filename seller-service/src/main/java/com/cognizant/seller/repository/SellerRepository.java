package com.cognizant.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.seller.model.Seller;

/**
 * @author Arkit Das
 */
@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

}
