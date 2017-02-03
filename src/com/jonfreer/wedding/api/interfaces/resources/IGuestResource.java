package com.jonfreer.wedding.api.interfaces.resources;

import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.servicemodel.Guest;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Defines the interface for resources that wish to interact
 * with guest resources.
 *
 * @author jonfreer
 * @since 11/13/2013.
 */
@Path("/guests")
public interface IGuestResource {

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
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    Response getGuests(
    		@Context Request request,
    		@Context UriInfo uriInfo,
            @QueryParam("givenName") String givenName,
            @QueryParam("surname") String surname,
            @QueryParam("inviteCode") String inviteCode);

    /**
     * Creates a new guest resource and appends it to the /guests/ resource collection.
     *
     * @param desiredGuestState The desired state for the guest resource being created.
     * @return javax.ws.rs.Response with an HTTP status of 201 - Created on success.
     * @throws ResourceNotFoundException Thrown when the newly created guest resource
     *                                   cannot be found.
     */
    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    Response createGuest(
    		@Context UriInfo uriInfo, 
    		Guest desiredGuestState) throws ResourceNotFoundException;

    /**
     * Retrieves the current state of the guest resources with the id provided.
     *
     * @param id The id of the guest resource being retrieved.
     * @return javax.ws.rs.Response with an HTTP status of 200 - OK on success.
     * @throws ResourceNotFoundException Thrown when a guest resource with the
     *                                   provided id cannot be found.
     */
    @Path("{id : \\d+}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    Response getGuest(
    		@Context Request request,
    		@Context UriInfo uriInfo, 
    		@PathParam("id") int id) throws ResourceNotFoundException;

    /**
     * Replaces the current state of the guest resource with the id provided.
     *
     * @param id                The id of the guest resource to be updated.
     * @param desiredGuestState The desired state for the guest resource being updated.
     * @return javax.ws.rs.core.Response with an HTTP status of 200 - OK on success.
     * @throws ResourceNotFoundException Thrown when the guest whose state is being
     *                                   overwritten can not be found.
     */
    @Path("{id : \\d+}")
    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    Response updateGuest(
    		@Context Request request, 
    		@Context UriInfo uriInfo, 
    		@PathParam("id") int id, 
    		Guest desiredGuestState) throws ResourceNotFoundException;

    /**
     * Deletes the guest resource with the id provided.
     *
     * @param id The id of the guest resource to be deleted.
     * @return javax.ws.rs.core.Response with an HTTP status code of 204 - No Content
     * on success.
     * @throws ResourceNotFoundException Thrown when a guest with the provided identifier
     *                                   cannot be found.
     */
    @Path("{id : \\d+}")
    @DELETE
    Response deleteGuest(
    		@Context UriInfo uriInfo, 
    		@PathParam("id") int id) throws ResourceNotFoundException;
}
