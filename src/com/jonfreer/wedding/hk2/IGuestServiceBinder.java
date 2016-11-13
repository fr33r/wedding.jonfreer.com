package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.application.services.GuestService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by jonfreer on 11/13/16.
 */
public class IGuestServiceBinder extends AbstractBinder {

    @Override
    protected void configure() {
        this.bind(GuestService.class).to(IGuestService.class);
    }
}
