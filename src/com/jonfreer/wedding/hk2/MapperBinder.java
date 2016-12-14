package com.jonfreer.wedding.hk2;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A specific abstract binder implementation that binds the
 * DozerBeanMapper class to the Mapper interface within the HK2 container.
 * @author jonfreer
 * @since 11/13/16
 */
public class MapperBinder extends AbstractBinder {

    /**
     * Configures the binding between the DozerBeanMapper class
     * and the Mapper interface.
     */
    @Override
    protected void configure() {
        this.bind(new DozerBeanMapper()).to(Mapper.class);
    }
}
