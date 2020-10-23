package com.bank.demo.service;

import com.bank.demo.dto.UserDto;

public interface UserService {


	public String register(UserDto userDto) throws Exception;

	public String depositAmount(String login, Long amount) throws Exception;

	public String withdrawAmount(String login, Long amount) throws Exception;

	public String checkBalance(String login) throws Exception;

	public String login(String login) throws Exception;

	
}
