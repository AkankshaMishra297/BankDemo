package com.bank.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.demo.dto.UserDto;
import com.bank.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	/*
	 *for registering the user
	 *userDto is user details
	 */
	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody UserDto userDto) throws Exception {
		
		LOGGER.info("Starting register() from UserController with arguments:: userDto:{} ", userDto);
		ResponseEntity<?> responseEntity = null;
		String jsonString = userService.register(userDto);
		if(jsonString != null){
			responseEntity = ResponseEntity.ok(jsonString);
		} else
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		LOGGER.info("Exiting register() from UserController with return:: responseEntity:{} ", responseEntity);
		return responseEntity;
	}

	/*
	 * for depositing the amount
	 * @param login is used to check user is authenticated or not
	 */
	@PostMapping(value = "/deposit/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> depositAmount(@RequestBody String login, @PathVariable("amount") Long amount) throws Exception {
		
		LOGGER.info("Starting depositAmount() from UserController with arguments login:{} amount:{} ", login, amount);
		ResponseEntity<?> responseEntity = null;
		String jsonString = userService.depositAmount(login, amount);
		if(jsonString != null){
			responseEntity = ResponseEntity.ok(jsonString);
		} else
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		LOGGER.info("Exiting depositAmount() from UserController with return:: responseEntity:{} ", responseEntity);
		return responseEntity;
	}

	/*
	 * for withdrawing the amount
	 * @param login is used to check user is authenticated or not
	 */
	@PostMapping(value = "/withdraw/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> withdrawAmount(@RequestBody String login, @PathVariable("amount") Long amount) throws Exception {
		
		LOGGER.info("Starting withdrawAmount() from UserController with arguments login:{} amount:{} ",login, amount);
		ResponseEntity<?> responseEntity = null;
		String jsonString = userService.withdrawAmount(login, amount);
		if(jsonString != null){
			responseEntity = ResponseEntity.ok(jsonString);
		} else
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		LOGGER.info("Exiting withdrawAmount() from UserController with return:: responseEntity:{} ", responseEntity);
		return responseEntity;
	}

	/*
	 * for checking the amount
	 * @param login is used to check user is authenticated or not
	 */
	@PostMapping(value = "/checkBalance", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> checkBalance(@RequestBody String login) throws Exception {
		
		LOGGER.info("Starting checkBalance() from UserController with arguments login:{}", login);
		ResponseEntity<?> responseEntity = null;
		String jsonString = userService.checkBalance(login);
		if(jsonString != null){
			responseEntity = ResponseEntity.ok(jsonString);
		} else
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		LOGGER.info("Exiting checkBalance() from UserController with return:: responseEntity:{} ", responseEntity);
		return responseEntity;
	}
	
	/*
	 * for login
	 * @param login is credential
	 */
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody String login) throws Exception {
		
		LOGGER.info("Starting login() from UserController with arguments login:{}",login);
		ResponseEntity<?> responseEntity = null;
		String jsonString = userService.login(login);
		if(jsonString != null){
			responseEntity = ResponseEntity.ok(jsonString);
		} else
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		LOGGER.info("Exiting login() from UserController with return:: responseEntity:{} ", responseEntity);
		return responseEntity;
	}


}
