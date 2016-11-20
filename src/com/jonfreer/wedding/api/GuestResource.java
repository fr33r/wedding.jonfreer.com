package com.jonfreer.wedding.api;

import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.servicemodel.Guest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;

/**
 * JAX-RS resource class representing a wedding guest resource.
 */
public class GuestResource implements IGuestResource {

    @Inject
    private IGuestService guestService;

    public GuestResource() {
    }

    /**
     * Retrieves the collection of guest resources.
     *
     * @return The collection of guest resources.
     */
    @Override
    public Response getGuests() {
        ArrayList<Guest> guests = this.guestService.getGuests();
        return Response.ok(guests).build();
    }

    /**
     * Creates a new guest resource and appends it to the /guests/ resource collect
     *
     * @param desiredGuestState The desired state for the guest resource being created.
     * @return javax.ws.rs.Response with an HTTP status of 201 - Created on success.
     */
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

    /**
     * Retrieves the current state of the guest resources with the id provided.
     *
     * @param id The id of the guest resource being retrieved.
     * @return javax.ws.rs.Response with an HTTP status of 200 - OK on success.
     */
    @Override
    public Response getGuest(int id) {
        Guest guest;
        try {
            guest = this.guestService.getGuest(id);
            return Response.ok(guest).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    /**
     * Replaces the current state of the guest resource with the id provided.
     *
     * @param id                The id of the guest resource to be updated.
     * @param desiredGuestState The desired state for the guest resource being updated.
     * @return javax.ws.rs.core.Response with an HTTP status of 200 - OK on success.
     */
    @Override
    public Response updateGuest(int id, Guest desiredGuestState) {
        try {
            this.guestService.updateGuest(desiredGuestState);
            Guest guest = guestService.getGuest(id);
            return Response.ok(guest).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    /**
     * Deletes the guest resource with the id provided.
     *
     * @param id The id of the guest resource to be deleted.
     * @return javax.ws.rs.core.Response with an HTTP status code of 204 - No Content
     * on success.
     */
    @Override
    public Response deleteGuest(int id) {
        try {
            this.guestService.deleteGuest(id);
            return Response.noContent().build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
