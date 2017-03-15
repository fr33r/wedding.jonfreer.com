package com.jonfreer.wedding.api.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import com.jonfreer.wedding.application.interfaces.services.IResourceMetadataService;
import com.jonfreer.wedding.servicemodel.metadata.ResourceMetadata;

@Provider
public class ConditionalPutFilter implements ContainerRequestFilter {

	private IResourceMetadataService resourceMetadataService;
	
	@Inject
	public ConditionalPutFilter(IResourceMetadataService resourceMetadataService){
		this.resourceMetadataService = resourceMetadataService;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		Request request = requestContext.getRequest();
		UriInfo uriInfo = requestContext.getUriInfo();
		
		if(request.getMethod().equalsIgnoreCase("PUT")){
			
			ResourceMetadata resourceMetadata = 
				this.resourceMetadataService.getResourceMetadata(uriInfo.getRequestUri());
			
			if(resourceMetadata != null){
				
				ResponseBuilder responseBuilder = 
					request.evaluatePreconditions(
						resourceMetadata.getLastModified(), 
						new EntityTag(resourceMetadata.getEntityTag()));
				
				if(responseBuilder != null){
					requestContext.abortWith(responseBuilder.build());
				}
			}
		}
	}
}
