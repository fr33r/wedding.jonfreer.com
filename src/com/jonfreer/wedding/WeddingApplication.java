/*package com.jonfreer.wedding;

import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;
import com.jonfreer.wedding.api.*;
import javax.ws.rs.ApplicationPath;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@ApplicationPath("/resources")
public class WeddingApplication extends Application {
	
	public WeddingApplication(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(GuestResource.class);
        s.add(GeneralExceptionMapper.class);
        s.add(JacksonJsonProvider.class);
        return s;
    }
}
*/