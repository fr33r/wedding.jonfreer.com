package com.jonfreer.wedding.hk2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.jonfreer.wedding.infrastructure.services.EntityTagService;

/**
 * A specific abstract binder implementation that binds the
 * EntityTagService class to the EntityTagService interface within the HK2 container.
 *
 * @author jonfreer
 * @since 03/26/2017
 */
public class EntityTagServiceBinder extends AbstractBinder {

	/**
     * Configures the binding between the EntityTagService class
     * and the EntityTagService interface.
     */
	@Override
	protected void configure() {
		this.bind(EntityTagService.class).to(com.jonfreer.wedding.infrastructure.interfaces.services.EntityTagService.class);
	}

}
