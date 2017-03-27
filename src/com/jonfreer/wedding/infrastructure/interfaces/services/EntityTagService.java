package com.jonfreer.wedding.infrastructure.interfaces.services;

import javax.ws.rs.core.EntityTag;

import org.jvnet.hk2.annotations.Contract;

/**
 * 
 * @author jonfreer
 * @since 03/26/2017
 */
@Contract
public interface EntityTagService {

	/**
	 * 
	 * @param entity
	 * @return
	 */
	EntityTag get(Object entity);
}
