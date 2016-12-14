package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.infrastructure.factories.ExceptionRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IExceptionRepositoryFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A specific abstract binder implementation that binds the
 * ExceptionRepositoryFactory class to the IExceptionRepositoryFactory
 * interface within the HK2 container.
 * @author jonfreer
 * @since 12/11/16
 */
public class IExceptionRepositoryFactoryBinder extends AbstractBinder {

    /**
     * Configures the binding between the ExceptionRepositoryFactory class
     * and the IExceptionRepositoryFactory interface.
     */
    @Override
    protected void configure() {
        this.bind(ExceptionRepositoryFactory.class).to(IExceptionRepositoryFactory.class);
    }
}
