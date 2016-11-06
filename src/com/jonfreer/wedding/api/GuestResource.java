package com.jonfreer.wedding.api;

/*
 * Application dependencies.
 */
import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.application.services.GuestService;
import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.infrastructure.factories.DatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.infrastructure.factories.GuestRepositoryFactory;
import com.jonfreer.wedding.infrastructure.factories.ReservationRepositoryFactory;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.MediaType;

import java.net.URI;
/*
 * Java SE dependencies.
 */
import java.util.ArrayList;

@Path("/guests")
public class GuestResource{

	private IGuestService guestService;

	public GuestResource(){
		guestService = 
				new GuestService(
						new GuestRepositoryFactory(),
						new ReservationRepositoryFactory(),
						new DatabaseUnitOfWorkFactory(
								"jdbc:mysql://jonfreer.com:3306/jonfreer_wedding", 
								"jonfreer_creator", "__Goalie31__")
						);
	}

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getGuests(){
		ArrayList<Guest> guests = this.guestService.getGuests();
		return Response.ok(guests).build();
	}

	@POST
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response createGuest(Guest desiredGuestState){
		try {
			int guestId = this.guestService.insertGuest(desiredGuestState);
			Guest guest = this.guestService.getGuest(guestId);
			return Response.created(URI.create("/guests/" + guestId)).entity(guest).build();
		} catch (ResourceNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Path("{id : \\d+}")
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getGuest(@PathParam("id") int id){
		Guest guest;
		try {
			guest = this.guestService.getGuest(id);
			return Response.ok(guest).build();
		} catch (ResourceNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}	  
	}

	@Path("{id : \\d+}")
	@PUT
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response updateGuest(@PathParam("id") int id, Guest desiredGuestState){
		try {
			this.guestService.updateGuest(desiredGuestState);
			Guest guest = guestService.getGuest(id);
			return Response.ok(guest).build();
		} catch (ResourceNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@Path("{id : \\d+}")
	@DELETE
	public Response deleteGuest(@PathParam("id") int id){
		try {
			this.guestService.deleteGuest(id);
			return Response.noContent().build();
		} catch (ResourceNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		} 
	}

}
