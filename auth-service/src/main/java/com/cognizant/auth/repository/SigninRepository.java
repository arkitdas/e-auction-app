package com.cognizant.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.auth.entity.Signin;

@Repository
public interface SigninRepository extends JpaRepository<Signin, Long> {

    Optional<Signin> findByUsernameAndPassword(String username, String password);
}
