package com.jonfreer.wedding.api;

import com.jonfreer.wedding.domain.Guest;

/*
  JAX-RS dependencies.
*/
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/guests")
public class GuestResource{

  @GET
  @Produces("application/json")
  public Response getGuests(){

  }

  @POST
  @Produces("application/json")
  @Consumes("application/json")
  public Response createGuest(Guest desiredGuestState){

  }

  @Path("{id : \\d+}")
  @GET
  @Produces("application/json")
  public Response getGuest(@PathParam("id") int id){

  }

  @Path("{id : \\d+}")
  @PUT
  @Produces("application/json")
  @Consumes("application/json")
  public Response updateGuest(@PathParam("id") int id, Guest desiredGuestState){

  }

  @Path("{id : \\d+}")
  @DELETE
  public Response deleteGuest(@PathParam("id") int id){

  }
  
}
