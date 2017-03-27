/**
 * 
 */
package com.jonfreer.wedding.infrastructure.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.ws.rs.core.EntityTag;

import org.jvnet.hk2.annotations.Service;

import java.nio.charset.Charset;

/**
 * @author jonfreer
 *
 */
@Service
public class EntityTagService implements com.jonfreer.wedding.infrastructure.interfaces.services.EntityTagService {

	@Override
	public EntityTag get(Object entity) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] bytesMD5 = digest.digest(entity.toString().getBytes(Charset.forName("UTF-8")));
			String entityTagStringBase64Encoded = 
					Base64.getEncoder().encodeToString(bytesMD5);
			
			return new EntityTag(entityTagStringBase64Encoded);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
