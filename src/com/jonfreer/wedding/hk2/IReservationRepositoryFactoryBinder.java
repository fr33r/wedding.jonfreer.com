package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.infrastructure.factories.ReservationRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IReservationRepositoryFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by jonfreer on 11/13/16.
 */
public class IReservationRepositoryFactoryBinder extends AbstractBinder {

    @Override
    protected void configure() {
        this.bind(ReservationRepositoryFactory.class).to(IReservationRepositoryFactory.class);
    }
}
