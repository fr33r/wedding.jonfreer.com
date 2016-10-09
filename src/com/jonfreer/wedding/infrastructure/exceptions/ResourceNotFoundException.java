package com.jonfreer.wedding.infrastructure.exceptions;

public class ResourceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int resourceId;

	public ResourceNotFoundException(String message, int resourceId){
		super(message);
		this.resourceId = resourceId;
	}

	public int getResourceId(){
		return this.resourceId;
	}
}
