package com.jonfreer.wedding.hk2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.jonfreer.wedding.infrastructure.factories.DatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;

/**
 * A specific abstract binder implementation that binds the
 * DatabaseUnitOfWorkFactory class to the IDatabaseUnitOfWorkFactory
 * interface within the HK2 container.
 * @author jonfreer
 * @since 11/13/16
 */
public class IDatabaseUnitOfWorkFactoryBinder extends AbstractBinder {

    /**
     * Configures the binding between the DatabaseUnitOfWorkFactory class
     * and the IDatabaseUnitOfWorkFactory interface.
     */
    @Override
    protected void configure() {
        this.bind(DatabaseUnitOfWorkFactory.class).to(IDatabaseUnitOfWorkFactory.class);
    }

}
