package com.jonfreer.wedding.api.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
public class CacheControlFilter implements ContainerResponseFilter {

	@Override
	public void filter(
		ContainerRequestContext requestContext, 
		ContainerResponseContext responseContext) throws IOException {
		
		//do i really need this? there has got to be a way
		//that response filters do not run when an exception occurs.
		if(responseContext.getStatus() >= 400){ return; }
		
		Request request = requestContext.getRequest();
		
		if(request.getMethod().equalsIgnoreCase("GET")){
			
			UriInfo uriInfo = requestContext.getUriInfo();
			
			//for now, not providing caching abilities of search results.
			if(uriInfo.getQueryParameters().isEmpty()){
				
				CacheControl cacheControl = new CacheControl();
				cacheControl.setPrivate(true);
				cacheControl.setMaxAge(300);
				
				responseContext.getHeaders().add("Cache-Control", cacheControl);
			}
		}
	}

}
