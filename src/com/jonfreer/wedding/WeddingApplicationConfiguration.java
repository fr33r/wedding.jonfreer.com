package com.jonfreer.wedding;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.jonfreer.wedding.api.GuestResource;
import com.jonfreer.wedding.api.IGuestResource;
import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.hk2.*;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

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

        this.register(GuestResource.class);
        this.register(new GeneralExceptionMapper());
        this.register(new JacksonJsonProvider());

        //HK2 Binders.
        this.register(new IGuestServiceBinder());
        this.register(new IGuestRepositoryFactoryBinder());
        this.register(new IReservationRepositoryFactoryBinder());
        this.register(new IDatabaseUnitOfWorkFactoryBinder());
        this.register(new MapperBinder());
    }
}
