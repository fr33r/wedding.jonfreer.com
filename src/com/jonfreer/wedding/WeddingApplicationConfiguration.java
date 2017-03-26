package com.jonfreer.wedding;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.jonfreer.wedding.api.resources.GuestResource;
import com.jonfreer.wedding.api.exceptionmappers.GeneralExceptionMapper;
import com.jonfreer.wedding.api.exceptionmappers.NotFoundExceptionMapper;
import com.jonfreer.wedding.api.filters.CacheControlFilter;
import com.jonfreer.wedding.api.filters.ConditionalGetFilter;
import com.jonfreer.wedding.api.filters.ConditionalPutFilter;
import com.jonfreer.wedding.hk2.IGuestServiceBinder;
import com.jonfreer.wedding.hk2.EntityTagServiceBinder;
import com.jonfreer.wedding.hk2.IDatabaseUnitOfWorkFactoryBinder;
import com.jonfreer.wedding.hk2.IExceptionRepositoryFactoryBinder;
import com.jonfreer.wedding.hk2.IReservationRepositoryFactoryBinder;
import com.jonfreer.wedding.hk2.IGuestRepositoryFactoryBinder;
import com.jonfreer.wedding.hk2.ResourceMetadataServiceBinder;
import com.jonfreer.wedding.hk2.MapperBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by jonfreer on 11/9/16.
 */
public class WeddingApplicationConfiguration extends ResourceConfig {

    public WeddingApplicationConfiguration() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //JAX-RS Components.
        this.register(GuestResource.class);
        this.register(GeneralExceptionMapper.class);
        this.register(NotFoundExceptionMapper.class);
        this.register(JacksonJsonProvider.class);
        this.register(CacheControlFilter.class);
        this.register(ConditionalGetFilter.class);
        this.register(ConditionalPutFilter.class);

        //HK2 Binders.
        this.register(new IGuestServiceBinder());
        this.register(new IGuestRepositoryFactoryBinder());
        this.register(new IReservationRepositoryFactoryBinder());
        this.register(new IExceptionRepositoryFactoryBinder());
        this.register(new IDatabaseUnitOfWorkFactoryBinder());
        this.register(new ResourceMetadataServiceBinder());
        this.register(new MapperBinder());
        this.register(new EntityTagServiceBinder());
    }
}
