package com.bank.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.demo.model.Account;

public interface AccountRepo extends JpaRepository<Account, Integer> {


}
