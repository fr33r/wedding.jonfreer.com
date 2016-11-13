package com.jonfreer.wedding.api;

import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.domain.Guest;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;

public class GuestResource implements IGuestResource{

    @Inject
    private IGuestService guestService;

    public GuestResource(){}
    
    @Override
    public Response getGuests() {
        ArrayList<Guest> guests = this.guestService.getGuests();
        return Response.ok(guests).build();
    }

    @Override
    public Response createGuest(Guest desiredGuestState) {
        try {
            int guestId = this.guestService.insertGuest(desiredGuestState);
            Guest guest = this.guestService.getGuest(guestId);
            return Response.created(URI.create("/guests/" + guestId)).entity(guest).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response getGuest(@PathParam("id") int id) {
        Guest guest;
        try {
            guest = this.guestService.getGuest(id);
            return Response.ok(guest).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response updateGuest(@PathParam("id") int id, Guest desiredGuestState) {
        try {
            this.guestService.updateGuest(desiredGuestState);
            Guest guest = guestService.getGuest(id);
            return Response.ok(guest).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response deleteGuest(@PathParam("id") int id) {
        try {
            this.guestService.deleteGuest(id);
            return Response.noContent().build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
