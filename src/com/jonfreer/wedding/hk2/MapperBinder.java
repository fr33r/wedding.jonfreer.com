package com.jonfreer.wedding.hk2;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * @author jonfreer
 * @since 11/20/16
 */
public class MapperBinder extends AbstractBinder {

    @Override
    protected void configure() {
        this.bind(new DozerBeanMapper()).to(Mapper.class);
    }
}
