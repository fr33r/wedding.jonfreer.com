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

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * JAX-RS resource class representing a wedding guest resource.
 */
public class GuestResource implements IGuestResource {

    @Inject
    private IGuestService guestService;

    @Inject
    private IResourceMetadataService resourceMetadataService;

    public GuestResource() {}

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
        String location = 
        		uriInfo.getRequestUri().toString() + guestId + "/";
        this.resourceMetadataService.insertResourceMetadata(
        		new ResourceMetadata(location, lastModified, entityTag)
		);
        
        return Response
        		.created(URI.create(location))
        		.entity(guest)
        		.build();
    }

    /**
     * Retrieves the current state of the guest resources with the id provided.
     * 
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/If-None-Match
     * Note that the server generating a 304 response MUST generate any of the 
     * following header fields that would have been sent in a 200 (OK) response 
     * to the same request: Cache-Control, Content-Location, Date, ETag, Expires, and Vary.
     * 
     * https://tools.ietf.org/html/draft-ietf-httpbis-p4-conditional-18#section-4.1
     * A 304 response MUST include a Date header field (Section 9.2 of
     * [Part2]) unless the origin server does not have a clock that can
     * provide a reasonable approximation of the current time.  If a 200
     * response to the same request would have included any of the header
     * fields Cache-Control, Content-Location, ETag, Expires, Last-Modified,
     * or Vary, then those same header fields MUST be sent in a 304
     * response.
     *
     * @param id The id of the guest resource being retrieved.
     * @return javax.ws.rs.Response with an HTTP status of 200 - OK on success.
     */
    @Override
    public Response getGuest(
    		Request request, 
    		UriInfo uriInfo, 
    		int id) throws ResourceNotFoundException {

        Guest guest = this.guestService.getGuest(id);
             
    	ResourceMetadata resourceMetadata = 
		this.resourceMetadataService.getResourceMetadata(uriInfo.getRequestUri());
        
        if(resourceMetadata == null){
        	
        	Date lastModified = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
            String entityTag = 
            		EntityTagGenerator.generate(guest.toString().getBytes(), true);
            this.resourceMetadataService.insertResourceMetadata(
            		new ResourceMetadata(uriInfo.getRequestUri().toString(), lastModified, entityTag)
    		);
            resourceMetadata = 
            		this.resourceMetadataService.getResourceMetadata(uriInfo.getRequestUri());
        }
        
        return Response
        			.ok(guest)
    				.header("Last-Modified", resourceMetadata.getLastModified())
					.tag(new EntityTag(resourceMetadata.getEntityTag()))
					.build();
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
 
        this.guestService.updateGuest(desiredGuestState);
        Guest guest = guestService.getGuest(id);
        
        ResponseBuilder responseBuilder = Response.ok(guest);
        		
    	ResourceMetadata resourceMetadata = 
			this.resourceMetadataService.getResourceMetadata(uriInfo.getRequestUri());
        
        if(resourceMetadata != null){
        	
        	//update resource metadata.
        	Date lastModified = new Date();
    		String entityTagStringUrlEncoded = 
    				EntityTagGenerator.generate(guest.toString().getBytes(), true);
    		this.resourceMetadataService.updateResourceMetaData(
    				new ResourceMetadata(uriInfo.getRequestUri().toString(), lastModified, entityTagStringUrlEncoded)
    		);
    		
    		resourceMetadata = 
    				this.resourceMetadataService.getResourceMetadata(uriInfo.getRequestUri());
    		
    		responseBuilder
    			.header("Last-Modified", resourceMetadata.getLastModified())
    			.tag(new EntityTag(resourceMetadata.getEntityTag()));
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
