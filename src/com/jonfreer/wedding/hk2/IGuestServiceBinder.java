package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.application.services.GuestService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A specific abstract binder implementation that binds the
 * GuestService class to the IGuestService interface within the HK2 container.
 *
 * @author jonfreer
 * @since 11/13/16
 */
public class IGuestServiceBinder extends AbstractBinder {

    /**
     * Configures the binding between the GuestService class
     * and the IGuestService interface.
     */
    @Override
    protected void configure() {
        this.bind(GuestService.class).to(IGuestService.class);
    }
}
