/**
 * 
 */
package com.jonfreer.wedding.hk2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.jonfreer.wedding.infrastructure.services.ResourceMetadataService;

/**
 * @author jonfreer
 *
 */
public class ResourceMetadataServiceBinder extends AbstractBinder {

	@Override
	protected void configure() {
		this.bind(ResourceMetadataService.class).to(com.jonfreer.wedding.infrastructure.interfaces.services.ResourceMetadataService.class);
	}

}
