package com.bank.demo.common;

public class CommonResponse {
	
	private String Status;
	
	private String Message;
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	@Override
	public String toString() {
		return "CommonResponse [Status=" + Status + ", Message=" + Message + "]";
	}	
	
}
