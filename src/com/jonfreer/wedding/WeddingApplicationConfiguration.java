package com.jonfreer.wedding;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.jonfreer.wedding.api.GuestResource;
import com.jonfreer.wedding.hk2.IDatabaseUnitOfWorkFactoryBinder;
import com.jonfreer.wedding.hk2.IGuestRepositoryFactoryBinder;
import com.jonfreer.wedding.hk2.IGuestServiceBinder;
import com.jonfreer.wedding.hk2.IReservationRepositoryFactoryBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by jonfreer on 11/9/16.
 */
@ApplicationPath("/resources")
public class WeddingApplicationConfiguration extends ResourceConfig {

    public WeddingApplicationConfiguration() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.register(new GeneralExceptionMapper());
        this.register(new JacksonJsonProvider());
        this.register(new IGuestServiceBinder());
        this.register(new IGuestRepositoryFactoryBinder());
        this.register(new IReservationRepositoryFactoryBinder());
        this.register(new IDatabaseUnitOfWorkFactoryBinder());

        this.packages(true, "com.jonfreer.wedding.api");
    }
}
