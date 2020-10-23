package com.bank.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {
	
	@JsonProperty("name")
	@NotNull(message = "bankname must not be null")
	@Pattern(regexp ="([a-zA-Z]){2,16}", message = "invalid bank name")
	private String name;
	
	@JsonProperty("accountNo")
	@NotNull(message = "account number must not be null")
	@Pattern(regexp ="^[0-9]*$", message = "invalid account number")
	private String accountNo;
	
	@JsonProperty("balance")
	@NotNull(message = "balance must not be null")
	@Pattern(regexp ="^[0-9]*$", message = "invalid balance")
	private String balance;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	
	
}
