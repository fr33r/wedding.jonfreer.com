package com.jonfreer.wedding.hk2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.jonfreer.wedding.infrastructure.services.LogService;

public class LogServiceBinder extends AbstractBinder {

	@Override
	protected void configure() {
		this.bind(LogService.class).to(com.jonfreer.wedding.infrastructure.interfaces.services.LogService.class);
	}

}
