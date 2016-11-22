package com.jonfreer.wedding.api.exceptionmappers;

public class ErrorResponse {
	private String message;
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
}
