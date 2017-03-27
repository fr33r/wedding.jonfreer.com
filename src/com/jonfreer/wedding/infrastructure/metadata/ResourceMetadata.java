package com.jonfreer.wedding.infrastructure.metadata;

import java.util.Date;
import java.net.URI;
import javax.ws.rs.core.EntityTag;

/**
 * Represents metadata about a single REST resource.
 * @author jonfreer
 * @since 03/26/2017
 */
public class ResourceMetadata {

    private URI uri;
    private Date lastModified;
    private EntityTag entityTag;
    
    /**
     * Constructs a ResourceMetadata instance, provided a URI,
     * last modified date and time, and an entity tag (also referred to as an ETag).
     * @param uri The URI of the resource.
     * @param lastModified The date and time that the resource identified by the URI
     *                     was modified.
     * @param entityTag The entity tag of the resource identified by the URI.
     */
    public ResourceMetadata(URI uri, Date lastModified, EntityTag entityTag){

        if(uri == null) {
            throw new IllegalArgumentException("The constructor argument 'uri' cannot be null.");
        }

        if(lastModified == null){
            throw new IllegalArgumentException("The constructor argument 'lastModified' cannot be null.");
        }

        if(entityTag == null){
            throw new IllegalArgumentException("The constructor argument 'entityTag' cannot be null.");
        }

        this.uri = uri;
        this.lastModified = (Date)lastModified.clone();
        this.entityTag = entityTag;
    }

    /**
     * Alters the URI that identifies the resource to
     * the URI provided.
     * @param uri The desired URI to identify the resource.
     */
//    public void setUri(URI uri) {
//		this.uri = uri;
//	}

    /**
     * Alters the date the time that indicates when the resource
     * was last modified to the date and time provided.
     * @param lastModified The desired date and time indicating
     * when the resource was last modified.
     */
//	public void setLastModified(Date lastModified) {
//		this.lastModified = lastModified;
//	}

	/**
	 * Alters the entity tag of the resource to the entity tag provided.
	 * @param entityTag The desired entity tag for the resource.
	 */
//	public void setEntityTag(EntityTag entityTag) {
//		this.entityTag = entityTag;
//	}

	/**
     * Retrieves the URI identifying the resource.
     * @return The URI identifying the resource.
     */
    public URI getUri(){
    	return this.uri;
    }

    /**
     * Retrieves the date and time that the resource
     * was last modified.
     * @return The date and time that the resource was
     * last modified.
     */
    public Date getLastModified(){
        return this.lastModified == null ? null : (Date)this.lastModified.clone();
    }

    /**
     * Retrieves the entity tag (also referred to as an ETag)
     * for the resource.
     * @return The entity tag for the resource.
     */
    public EntityTag getEntityTag(){
        return this.entityTag;
    }

    /**
     * Determines if a provided object is semantically
     * equivalent to the calling ResourceMetadata instance.
     * @param object An instance of Object to be compared with
     *               the calling ResourceMetadata instance.
     * @return true if the Object instance provided is an instance
     * of ResourceMetadata and is semantically equivalent to the calling
     * ResourceMetadata instance; false otherwise.
     */
    @Override
	public boolean equals(Object obj) {
    	
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		ResourceMetadata other = (ResourceMetadata) obj;
		
		if ((this.entityTag == null && other.entityTag != null) || 
			(this.entityTag != null && other.entityTag == null) ||
			!entityTag.equals(other.entityTag)) {
			return false;
		} 
		
		if ((this.lastModified == null && other.lastModified != null) || 
			(this.lastModified != null && other.lastModified == null) ||
			!lastModified.equals(other.lastModified)) {
			return false;
		} 
		
		if ((uri == null && other.uri != null) || 
			(uri != null && other.uri == null) || 
			!uri.equals(other.uri)) {
			return false;
		} 
		
		return true;
	}

    /**
     * Generates a hashcode representative of the current state of the
     * calling ResourceMetadata instance.
     * @return The hashcode of the calling ResourceMetadata instance.
     */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entityTag == null) ? 0 : entityTag.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

    /**
     * Creates a string representation of the calling ResourceMetadata instance.
     * @return A string representation of the calling ResourceMetadata instance.
     */
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return builder
			.append("ResourceMetadata [uri=")
			.append(uri)
			.append(", lastModified=")
			.append(lastModified)
			.append(", entityTag=")
			.append(entityTag)
			.append("]")
			.toString();
	}
}
