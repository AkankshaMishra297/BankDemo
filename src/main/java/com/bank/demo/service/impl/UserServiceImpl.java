package com.bank.demo.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.demo.common.CommonConstants;
import com.bank.demo.common.CommonResponse;
import com.bank.demo.dto.UserDto;
import com.bank.demo.enumeration.StatusName;
import com.bank.demo.exception.EmailTakenException;
import com.bank.demo.exception.InsufficientAmountException;
import com.bank.demo.exception.InvalidCredentialException;
import com.bank.demo.model.Account;
import com.bank.demo.model.Status;
import com.bank.demo.model.User;
import com.bank.demo.repository.StatusRepo;
import com.bank.demo.repository.UserRepo;
import com.bank.demo.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();


	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private StatusRepo statusRepo;

	/*
	 * service for registering the user
	 */
	@Override
	public String register(UserDto userDto) throws Exception {

		LOGGER.info("Starting register() from UserServiceImpl with arguments:: userDto:{} ",userDto);
		String returnValue = null;
		String errorMsg = null;
		CommonResponse response = new CommonResponse();
		try {

			//for validating user details
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
			for (ConstraintViolation<UserDto> violation : violations) {
				LOGGER.error(violation.getMessage());
				response.setStatus(CommonConstants.FAIL);
				response.setMessage(violation.getMessage());
				returnValue = MAPPER.writeValueAsString(response);
				return returnValue;
			}
			
			if (userRepo.existsByEmail(userDto.getEmail())) 
				throw new EmailTakenException();

			User user = new User();
			Set<Status> roles = new HashSet<>();

			user.setFirstName(userDto.getFirstName() != null ? userDto.getFirstName() : null);
			user.setPassword(userDto.getPassword() != null ? userDto.getPassword() : null);
			user.setLastName(userDto.getLastName() != null ? userDto.getLastName() : null);
			user.setEmail(userDto.getEmail() != null ? userDto.getEmail() : null);			
			Status userRole = statusRepo.findByName(StatusName.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
			roles.add(userRole);
			user.setStatus(roles);

			if(userDto.getAccount() != null) {
				Account account = new Account();
				account.setUser(user);
				account.setName(userDto.getAccount().getName() != null ? userDto.getAccount().getName() : null);
				account.setAccountNo(userDto.getAccount().getAccountNo() != null ? userDto.getAccount().getAccountNo() : null);
				account.setBalance(userDto.getAccount().getBalance() != null ? Long.valueOf(userDto.getAccount().getBalance()) : null);
				user.setAccount(account);
			}

			User savedUser = this.userRepo.save(user);

			if(savedUser != null) {
				response.setStatus(CommonConstants.SUCCESS);
				response.setMessage(CommonConstants.REGISTER);
			}

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(errorMsg + "\n\r : "+e.getStackTrace());
			e.printStackTrace();
		}
		if(errorMsg != null){
			response.setStatus(CommonConstants.FAIL);
			response.setMessage(errorMsg);
		}

		returnValue = MAPPER.writeValueAsString(response);
		LOGGER.info("Exiting register() from UserServiceImpl with return:: returnValue:{} ",returnValue);
		return returnValue;
	}

	/*
	 * service for depositing amount
	 */
	@Override
	public String depositAmount(String login, Long amount) throws Exception {

		LOGGER.info("Starting depositAmount() from UserServiceImpl with arguments login:{} amount:{} ", login, amount);
		String returnValue = null;
		String errorMsg = null;
		CommonResponse response = new CommonResponse();

		try {
			JsonNode request = MAPPER.readTree(login);

			String email=request.get("email").asText();
			String password=request.get("password").asText();

			User loggedUser = userRepo.findByEmailAndPassword(email, password);

			if(loggedUser == null) 
				 throw new InvalidCredentialException();
				
			User user = userRepo.findByEmail(email);
			Long balance = user.getAccount().getBalance();
			Long totalBalance = balance + amount;
			user.getAccount().setBalance(totalBalance);

			User savedUser = this.userRepo.save(user);

			if(savedUser != null) {
				response.setStatus(CommonConstants.SUCCESS);
				response.setMessage(CommonConstants.DEPOSIT+" "+CommonConstants.BALANCE+" " +savedUser.getAccount().getBalance());
			}

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(errorMsg + "\n\r : "+e.getStackTrace());
			e.printStackTrace();
		}
		if(errorMsg != null){
			response.setStatus(CommonConstants.FAIL);
			response.setMessage(errorMsg);
		}

		returnValue = MAPPER.writeValueAsString(response);
		LOGGER.info("Exiting depositAmount() from UserServiceImpl with return:: returnValue:{} ",returnValue);
		return returnValue;
	}

	/*
	 * service for withdrawing amount
	 */	
	@Override
	public String withdrawAmount(String login, Long amount) throws Exception {

		LOGGER.info("Starting withdrawAmount() from UserServiceImpl with arguments login:{} amount:{} ", login, amount);
		String returnValue = null;
		String errorMsg = null;
		CommonResponse response = new CommonResponse();
		try {
			JsonNode request = MAPPER.readTree(login);

			String email=request.get("email").asText();
			String password=request.get("password").asText();

			User loggedUser = userRepo.findByEmailAndPassword(email, password);

			if(loggedUser == null)
				throw new InvalidCredentialException();

			User user = userRepo.findByEmail(email);
			Long balance = user.getAccount().getBalance();

			if(balance < amount)
				throw new InsufficientAmountException();

			Long totalBalance = balance - amount;
			user.getAccount().setBalance(totalBalance);


			User savedUser = this.userRepo.save(user);

			if(savedUser != null) {
				response.setStatus(CommonConstants.SUCCESS);
				response.setMessage(CommonConstants.WITHDRAW+" "+CommonConstants.BALANCE+" " +savedUser.getAccount().getBalance());
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(errorMsg + "\n\r : "+e.getStackTrace());
			e.printStackTrace();
		}
		if(errorMsg != null){
			response.setStatus(CommonConstants.FAIL);
			response.setMessage(errorMsg);
		}

		returnValue = MAPPER.writeValueAsString(response);
		LOGGER.info("Exiting withdrawAmount() from UserServiceImpl with return:: returnValue:{} ",returnValue);
		return returnValue;
	}

	/*
	 * service for checking the balance
	 */
	@Override
	public String checkBalance(String login) throws Exception {
		LOGGER.info("Starting checkBalance() from UserServiceImpl with arguments:: login:{} ",login);
		String returnValue = null;
		String errorMsg = null;
		CommonResponse response = new CommonResponse();
		try {
			JsonNode request = MAPPER.readTree(login);

			String email=request.get("email").asText();
			String password=request.get("password").asText();

			User loggedUser = userRepo.findByEmailAndPassword(email, password);

			if(loggedUser == null)
				throw new InvalidCredentialException();

			User user = userRepo.findByEmail(email);
			Long balance = user.getAccount().getBalance();

			if(balance != null) {
				response.setStatus(CommonConstants.SUCCESS);
				response.setMessage(CommonConstants.BALANCE+" " +balance);
			}

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(errorMsg + "\n\r : "+e.getStackTrace());
			e.printStackTrace();
		}
		if(errorMsg != null){
			response.setStatus(CommonConstants.FAIL);
			response.setMessage(errorMsg);
		}
		returnValue = MAPPER.writeValueAsString(response);
		LOGGER.info("Exiting checkBalance() from UserServiceImpl with return:: returnValue:{} ",returnValue);
		return returnValue;
	}	
	
	/*
	 * service for login
	 */
	@Override
	public String login(String login) throws Exception {

		LOGGER.info("Starting login() from UserServiceImpl with arguments:: login:{} ", login);
		String returnValue = null;
		String errorMsg = null;
		CommonResponse response = new CommonResponse();

		try {
			JsonNode request = MAPPER.readTree(login);

			String email=request.get("email").asText();
			String password=request.get("password").asText();

			User loggedUser = userRepo.findByEmailAndPassword(email, password);

			if(loggedUser == null) 
				 throw new InvalidCredentialException();
				
			User user = userRepo.findByEmail(email);

			if(user != null) {
				response.setStatus(CommonConstants.SUCCESS);
				response.setMessage(CommonConstants.WELCOME+" "+user.getFirstName()+","+CommonConstants.LOGIN);
			}

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(errorMsg + "\n\r : "+e.getStackTrace());
			e.printStackTrace();
		}
		if(errorMsg != null){
			response.setStatus(CommonConstants.FAIL);
			response.setMessage(errorMsg);
		}

		returnValue = MAPPER.writeValueAsString(response);
		LOGGER.info("Exiting login() from UserServiceImpl with return:: returnValue:{} ",returnValue);
		return returnValue;
	}

}

