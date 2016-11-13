package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.infrastructure.factories.GuestRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IGuestRepositoryFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by jonfreer on 11/13/16.
 */
public class IGuestRepositoryFactoryBinder extends AbstractBinder {

    @Override
    protected void configure() {
        this.bind(GuestRepositoryFactory.class).to(IGuestRepositoryFactory.class);
    }
}
