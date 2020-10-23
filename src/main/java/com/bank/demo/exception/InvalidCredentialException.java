package com.bank.demo.exception;

public class InvalidCredentialException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialException() {
		super("Email or Password is incorrect");
	}

}
