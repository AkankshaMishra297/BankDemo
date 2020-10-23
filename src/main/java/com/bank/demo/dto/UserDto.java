package com.bank.demo.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
	
	@JsonProperty("firstName")
	@NotNull(message = "firstname must not be null")
	@Pattern(regexp ="([a-zA-Z]){2,16}", message = "invalid firstName")
	private String firstName;
	
	@JsonProperty("lastName")
	@NotNull(message = "lasttname must not be null")
	@Pattern(regexp ="([a-zA-Z]){2,16}", message = "invalid lastName")
	private String lastName;
	
	@JsonProperty("password")
	@NotNull(message = "password must not be null")
    //@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,36}$", message = "Invalid Password")
	private String password;
	
	@JsonProperty("email")
	@NotNull(message = "email must not be null")
	@Pattern(regexp ="^([A-Za-z0-9])(([.])?[0-9a-z])*[@]([a-z])+([.]([a-z])+){1,3}", message = "invalid email")
	private String email;
	
	@JsonProperty("account")
	@Valid
	private AccountDto account;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(AccountDto account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "UserBean [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", email="
				+ email + ", account=" + account + "]";
	}
	
	
}
