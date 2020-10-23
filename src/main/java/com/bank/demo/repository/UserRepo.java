package com.bank.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.demo.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByEmailAndPassword(String email, String password);

	User findByEmail(String email);	
	
    Boolean existsByEmail(String email);

}
