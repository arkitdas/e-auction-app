package com.cognizant.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.user.model.User;

/**
 * @author Arkit Das
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUserType(String email);

}
