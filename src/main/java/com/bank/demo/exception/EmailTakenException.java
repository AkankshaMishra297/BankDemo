package com.bank.demo.exception;

public class EmailTakenException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public EmailTakenException() {
		super("Email is already used");
	}

}
