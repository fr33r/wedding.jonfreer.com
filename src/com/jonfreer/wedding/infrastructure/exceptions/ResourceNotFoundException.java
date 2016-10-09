package com.jonfreer.wedding.infrastructure.exceptions;

public class ResourceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private int resourceId;

	public ResourceNotFoundException(String message, int resourceId){
		this.message = message;
		this.resourceId = resourceId;
	}

	public String getMessage(){
		return this.message;
	}

	public int getResourceId(){
		return this.resourceId;
	}
}
