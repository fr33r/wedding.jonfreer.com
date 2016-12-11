package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.infrastructure.factories.ExceptionRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IExceptionRepositoryFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * @author jonfreer
 * @since 12/11/16
 */
public class IExceptionRepositoryFactoryBinder extends AbstractBinder {
    @Override
    protected void configure() {
        this.bind(ExceptionRepositoryFactory.class).to(IExceptionRepositoryFactory.class);
    }
}
