package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.infrastructure.factories.GuestRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IGuestRepositoryFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A specific abstract binder implementation that binds the
 * GuestRepositoryFactory class to the IGuestRepositoryFactory
 * interface within the HK2 container.
 *
 * @author jonfreer
 * @since 11/13/16
 */
public class IGuestRepositoryFactoryBinder extends AbstractBinder {

    /**
     * Configures the binding between the GuestRepositoryFactory class
     * and the IGuestRepositoryFactory interface.
     */
    @Override
    protected void configure() {
        this.bind(GuestRepositoryFactory.class).to(IGuestRepositoryFactory.class);
    }
}
