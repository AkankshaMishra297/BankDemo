package com.bank.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.demo.enumeration.StatusName;
import com.bank.demo.model.Status;

@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {
	
    Optional<Status> findByName(StatusName statusName);
}