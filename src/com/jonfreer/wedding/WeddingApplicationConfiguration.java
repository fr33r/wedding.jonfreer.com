package com.jonfreer.wedding;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.jonfreer.wedding.api.resources.GuestResource;
import com.jonfreer.wedding.api.exceptionmappers.GeneralExceptionMapper;
import com.jonfreer.wedding.api.exceptionmappers.NotFoundExceptionMapper;
import com.jonfreer.wedding.hk2.*;
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
        this.register(new GeneralExceptionMapper());
        this.register(new NotFoundExceptionMapper());
        this.register(new JacksonJsonProvider());

        //HK2 Binders.
        this.register(new IGuestServiceBinder());
        this.register(new IGuestRepositoryFactoryBinder());
        this.register(new IReservationRepositoryFactoryBinder());
        this.register(new IExceptionRepositoryFactoryBinder());
        this.register(new IDatabaseUnitOfWorkFactoryBinder());
        this.register(new IResourceMetadataRepositoryFactoryBinder());
        this.register(new MapperBinder());
    }
}
