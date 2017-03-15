package com.jonfreer.wedding.api.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

import com.jonfreer.wedding.application.interfaces.services.IResourceMetadataService;
import com.jonfreer.wedding.servicemodel.metadata.ResourceMetadata;

@Provider
public class ConditionalGetFilter implements ContainerRequestFilter {

	private IResourceMetadataService resourceMetadataService;
	
	@Inject
	public ConditionalGetFilter(
		IResourceMetadataService resourceMetadataService) {
		
		this.resourceMetadataService = resourceMetadataService;
	}
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		Request request = requestContext.getRequest();
		
		if(request.getMethod().equalsIgnoreCase("GET")){
			
			ResourceMetadata resourceMetadata =
				this.resourceMetadataService.getResourceMetadata(
					requestContext.getUriInfo().getRequestUri());
			
			if(resourceMetadata != null){
				ResponseBuilder responseBuilder = 
					request.evaluatePreconditions(
						resourceMetadata.getLastModified(), new EntityTag(resourceMetadata.getEntityTag()));
				
				if(responseBuilder != null){
					responseBuilder.header("Last-Modified", resourceMetadata.getLastModified());
					Response response = responseBuilder.build();
					
					requestContext.abortWith(response);
				}
			}	
		}
	}

}
