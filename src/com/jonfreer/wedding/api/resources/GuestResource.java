package com.jonfreer.wedding.api.resources;

import com.jonfreer.wedding.api.EntityTagGenerator;
import com.jonfreer.wedding.api.interfaces.resources.IGuestResource;
import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.application.interfaces.services.IResourceMetadataService;
import com.jonfreer.wedding.servicemodel.metadata.ResourceMetadata;
import com.jonfreer.wedding.servicemodel.Guest;
import com.jonfreer.wedding.servicemodel.GuestSearchCriteria;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.inject.Inject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

/**
 * JAX-RS resource class representing a wedding guest resource.
 */
public class GuestResource implements IGuestResource {

    @Inject
    private IGuestService guestService;

    @Inject
    private IResourceMetadataService resourceMetadataService;

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
    		Request request,
    		UriInfo uriInfo,
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
    public Response createGuest(
    		UriInfo uriInfo, 
    		Guest desiredGuestState) throws ResourceNotFoundException {
    	
        int guestId = this.guestService.insertGuest(desiredGuestState);
        Guest guest = this.guestService.getGuest(guestId);
        
        Date lastModified = new Date();
        String entityTag = 
        		EntityTagGenerator.generate(guest.toString().getBytes(), true);
        this.resourceMetadataService.insertResourceMetadata(
        		new ResourceMetadata(uriInfo.getRequestUri(), lastModified, entityTag)
		);
        
        return Response
        		.created(uriInfo.getRequestUri())
        		.entity(guest)
        		.header("Last-Modified", lastModified)
        		.header("ETag", entityTag)
        		.build();
    }

    /**
     * Retrieves the current state of the guest resources with the id provided.
     *
     * @param id The id of the guest resource being retrieved.
     * @return javax.ws.rs.Response with an HTTP status of 200 - OK on success.
     */
    @Override
    public Response getGuest(
    		Request request, 
    		UriInfo uriInfo, 
    		int id) throws ResourceNotFoundException {
    	
    	ResourceMetadata resourceMetadata = 
    			this.resourceMetadataService.getResourceMetadata(uriInfo.getRequestUri());
        
    	if(resourceMetadata != null){
    		
    		//check for conditional GET.
            EntityTag entityTag = new EntityTag(resourceMetadata.getEntityTag());
            ResponseBuilder responseBuilder = 
            		request.evaluatePreconditions(resourceMetadata.getLastModified(), entityTag);
            if(responseBuilder != null){
            	return responseBuilder.build();
            }
    	}
    	
        Guest guest = this.guestService.getGuest(id);
             
        ResponseBuilder responseBuilder = Response.ok(guest);
        
        if(resourceMetadata != null){
        	 		
    		responseBuilder
    			.header("Last-Modified", resourceMetadata.getLastModified())
    			.header("ETag", resourceMetadata.getEntityTag());
        }

        return responseBuilder.build();
    }

    /**
     * Replaces the current state of the guest resource with the id provided.
     *
     * @param id                The id of the guest resource to be updated.
     * @param desiredGuestState The desired state for the guest resource being updated.
     * @return javax.ws.rs.core.Response with an HTTP status of 200 - OK on success.
     * @throws NoSuchAlgorithmException 
     */
    @Override
    public Response updateGuest(
    		Request request, 
    		UriInfo uriInfo, 
    		int id, Guest desiredGuestState) throws ResourceNotFoundException{
    	
    	ResourceMetadata resourceMetadata = 
    			this.resourceMetadataService.getResourceMetadata(uriInfo.getRequestUri());
        
    	if(resourceMetadata != null){
    		
    		//check for conditional PUT.
            EntityTag entityTag = new EntityTag(resourceMetadata.getEntityTag());
            ResponseBuilder responseBuilder = 
            		request.evaluatePreconditions(resourceMetadata.getLastModified(), entityTag);
            if(responseBuilder != null){
            	return responseBuilder.build();
            }
    	}
        
        this.guestService.updateGuest(desiredGuestState);
        Guest guest = guestService.getGuest(id);
        
        ResponseBuilder responseBuilder = Response.ok(guest);
        		   
        if(resourceMetadata != null){
        	
        	//update resource metadata.
        	Date lastModified = new Date();
    		String entityTagStringUrlEncoded = 
    				EntityTagGenerator.generate(guest.toString().getBytes(), true);
    		this.resourceMetadataService.updateResourceMetaData(
    				new ResourceMetadata(uriInfo.getRequestUri(), lastModified, entityTagStringUrlEncoded)
    		);
    		
    		responseBuilder
    			.header("Last-Modified", lastModified)
    			.header("ETag", entityTagStringUrlEncoded);
        }
              
        return responseBuilder.build();
    }

    /**
     * Deletes the guest resource with the id provided.
     *
     * @param id The id of the guest resource to be deleted.
     * @return javax.ws.rs.core.Response with an HTTP status code of 204 - No Content
     * on success.
     */
    @Override
    public Response deleteGuest(
    		UriInfo uriInfo, 
    		int id) throws ResourceNotFoundException {
    	
        this.guestService.deleteGuest(id);
        this.resourceMetadataService.deleteResourceMetaData(uriInfo.getRequestUri());
        return Response.noContent().build();
    }
}
