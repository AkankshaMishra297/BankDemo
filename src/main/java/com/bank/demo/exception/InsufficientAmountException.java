package com.bank.demo.exception;

public class InsufficientAmountException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InsufficientAmountException() {
		super("You have not sufficient amount to withdraw ");
	}

}
