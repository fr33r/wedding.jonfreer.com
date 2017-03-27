package com.jonfreer.wedding.api.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import com.jonfreer.wedding.infrastructure.interfaces.services.ResourceMetadataService;
import com.jonfreer.wedding.infrastructure.metadata.ResourceMetadata;

@Provider
public class ConditionalPutFilter implements ContainerRequestFilter {

	private ResourceMetadataService resourceMetadataService;
	
	@Inject
	public ConditionalPutFilter(ResourceMetadataService resourceMetadataService){
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
						resourceMetadata.getEntityTag());
				
				if(responseBuilder != null){
					requestContext.abortWith(responseBuilder.build());
				}
			}
		}
	}
}
