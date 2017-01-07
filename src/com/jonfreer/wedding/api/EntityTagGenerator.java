/**
 * 
 */
package com.jonfreer.wedding.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author jonfreer
 *
 */
public class EntityTagGenerator {
	public static String generate(byte[] bytes, boolean urlEncoded){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] bytesMD5 = digest.digest(bytes);
			String entityTagStringBase64Encoded = 
					Base64.getEncoder().encodeToString(bytesMD5);
			
			if(urlEncoded){
				return URLEncoder.encode(entityTagStringBase64Encoded, "UTF8");
			}
			
			return entityTagStringBase64Encoded;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
}
