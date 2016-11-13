package com.jonfreer.wedding.hk2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.jonfreer.wedding.infrastructure.factories.DatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;

public class IDatabaseUnitOfWorkFactoryBinder extends AbstractBinder {

    @Override
    protected void configure() {
        this.bind(DatabaseUnitOfWorkFactory.class).to(IDatabaseUnitOfWorkFactory.class);
    }

}
