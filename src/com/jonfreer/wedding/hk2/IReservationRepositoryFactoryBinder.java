package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.infrastructure.factories.ReservationRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IReservationRepositoryFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A specific abstract binder implementation that binds the
 * ReservationRepositoryFactory class to the IReservationRepositoryFactory
 * interface within the HK2 container.
 * @author jonfreer
 * @since 11/13/16
 */
public class IReservationRepositoryFactoryBinder extends AbstractBinder {

    /**
     * Configures the binding between the ReservationRepositoryFactory class
     * and the IReservationRepositoryFactory interface.
     */
    @Override
    protected void configure() {
        this.bind(ReservationRepositoryFactory.class).to(IReservationRepositoryFactory.class);
    }
}
