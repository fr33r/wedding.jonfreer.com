package com.jonfreer.wedding.infrastructure.interfaces.services;

import org.jvnet.hk2.annotations.Contract;

/**
 * 
 * @author jonfreer
 * @since 03/26/2017
 */
@Contract
public interface LogService {

	void error(String message, String stacktrace);
	
	void error(Exception exception);
	
	void warning(String message);
	
	void info(String message);
	
	void debug(String message);
}
