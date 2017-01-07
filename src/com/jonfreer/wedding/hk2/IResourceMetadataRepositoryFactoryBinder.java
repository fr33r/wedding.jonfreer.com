package com.jonfreer.wedding.hk2;

import com.jonfreer.wedding.infrastructure.factories.ResourceMetadataRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IResourceMetadataRepositoryFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * @author jonfreer
 * @since 1/5/17
 */
public class IResourceMetadataRepositoryFactoryBinder extends AbstractBinder{

    @Override
    protected void configure() {
        this.bind(ResourceMetadataRepositoryFactory.class).to(IResourceMetadataRepositoryFactory.class);
    }
}
