package com.cognizant.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.product.model.Product;

/**
 * @author Arkit Das
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	public Optional<Product> findByProductIdAndCreatedByAndActive(String productId, String createdBy, boolean active);
	
	public Optional<Product> findByProductIdAndActive(String productId, boolean active);
	
	public Optional<List<Product>> findByCreatedByAndActive(String createdBy, boolean active);
	
	public Optional<List<Product>> findByActive(boolean active);
}
