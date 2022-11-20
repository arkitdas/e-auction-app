package com.eauction.authservice.repository;

import com.eauction.authservice.entity.Signin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SigninRepository extends JpaRepository<Signin, Long> {

    Optional<Signin> findByUsernameAndPassword(String username, String password);
}
