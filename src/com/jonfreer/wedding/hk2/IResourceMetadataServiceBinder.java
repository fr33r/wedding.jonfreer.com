/**
 * 
 */
package com.jonfreer.wedding.hk2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.jonfreer.wedding.application.interfaces.services.IResourceMetadataService;
import com.jonfreer.wedding.application.services.ResourceMetadataService;

/**
 * @author jonfreer
 *
 */
public class IResourceMetadataServiceBinder extends AbstractBinder {

	@Override
	protected void configure() {
		this.bind(ResourceMetadataService.class).to(IResourceMetadataService.class);
	}

}
