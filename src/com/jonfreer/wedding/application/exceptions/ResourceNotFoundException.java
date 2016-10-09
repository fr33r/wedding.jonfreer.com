/**
 * 
 */
package com.jonfreer.wedding.application.exceptions;

/**
 * @author jonfreer
 *
 */
public class ResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int resourceId;
	
	public ResourceNotFoundException(String message, Exception causingException, int resourceId){
		super(message, causingException);
		this.resourceId = resourceId;
	}

	public int getResourceId(){
		return this.resourceId;
	}
}
