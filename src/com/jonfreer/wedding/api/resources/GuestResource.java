package com.jonfreer.wedding.api.resources;

import com.jonfreer.wedding.api.interfaces.resources.IGuestResource;
import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.servicemodel.Guest;
import com.jonfreer.wedding.servicemodel.GuestSearchCriteria;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
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
     * Retrieves the collection of guest resources. Optional filter
     * criteria can be provided via query string parameters.
     * @param givenName When provided, filters the collection guest resources
     *                  that have a given name that matches.
     * @param surname When provided, filters the collection guest resources
     *                that have a surname (last name) that matches.
     * @param inviteCode When provided, filters the collection guest resources
     *                   that have an invite code that matches.
     * @return A response that contains a collection of guests.
     */
    @Override
    public Response getGuests(
            String givenName,
            String surname,
            String inviteCode){

        GuestSearchCriteria searchCriteria = null;
        if(givenName != null || surname != null || inviteCode != null){
            searchCriteria = new GuestSearchCriteria(givenName, surname, inviteCode);
        }
        ArrayList<Guest> guests = this.guestService.getGuests(searchCriteria);
        return Response.ok(guests).build();
    }

    /**
     * Creates a new guest resource and appends it to the /guests/ resource collect
     *
     * @param desiredGuestState The desired state for the guest resource being created.
     * @return javax.ws.rs.Response with an HTTP status of 201 - Created on success.
     */
    @Override
    public Response createGuest(Guest desiredGuestState) throws ResourceNotFoundException {
        int guestId = this.guestService.insertGuest(desiredGuestState);
        Guest guest = this.guestService.getGuest(guestId);
        return Response.created(URI.create("/guests/" + guestId + "/")).entity(guest).build();
    }

    /**
     * Retrieves the current state of the guest resources with the id provided.
     *
     * @param id The id of the guest resource being retrieved.
     * @return javax.ws.rs.Response with an HTTP status of 200 - OK on success.
     */
    @Override
    public Response getGuest(int id) throws ResourceNotFoundException {
        Guest guest = this.guestService.getGuest(id);
        return Response.ok(guest).build();
    }

    /**
     * Replaces the current state of the guest resource with the id provided.
     *
     * @param id                The id of the guest resource to be updated.
     * @param desiredGuestState The desired state for the guest resource being updated.
     * @return javax.ws.rs.core.Response with an HTTP status of 200 - OK on success.
     */
    @Override
    public Response updateGuest(int id, Guest desiredGuestState) throws ResourceNotFoundException {
        this.guestService.updateGuest(desiredGuestState);
        Guest guest = guestService.getGuest(id);
        return Response.ok(guest).build();
    }

    /**
     * Deletes the guest resource with the id provided.
     *
     * @param id The id of the guest resource to be deleted.
     * @return javax.ws.rs.core.Response with an HTTP status code of 204 - No Content
     * on success.
     */
    @Override
    public Response deleteGuest(int id) throws ResourceNotFoundException {
        this.guestService.deleteGuest(id);
        return Response.noContent().build();
    }
}
