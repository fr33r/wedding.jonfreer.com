package com.jonfreer.wedding.servicemodel.metadata;

import java.util.Date;

/**
 * Represents metadata about a single resource.
 * @author jonfreer
 * @since 1/4/17
 */
public class ResourceMetadata {

	private String uri;
    private Date lastModified;
    private String entityTag;

    /**
     * Constructs an empty instance of ResourceMetadata.
     */
    public ResourceMetadata(){
    	this.uri = null;
    	this.lastModified = null;
    	this.entityTag = null;
    }
    
    /**
     * Constructs a ResourceMetadata instance, provided a URI,
     * last modified date and time, and an entity tag (also referred to as an ETag).
     * @param uri The URI of the resource.
     * @param lastModified The date and time that the resource identified by the URI
     *                     was modified.
     * @param entityTag The entity tag of the resource identified by the URI.
     */
    public ResourceMetadata(String uri, Date lastModified, String entityTag){

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
    public void setUri(String uri) {
		this.uri = uri;
	}

    /**
     * Alters the date the time that indicates when the resource
     * was last modified to the date and time provided.
     * @param lastModified The desired date and time indicating
     * when the resource was last modified.
     */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Alters the entity tag of the resource to the entity tag provided.
	 * @param entityTag The desired entity tag for the resource.
	 */
	public void setEntityTag(String entityTag) {
		this.entityTag = entityTag;
	}

	/**
     * Retrieves the URI identifying the resource.
     * @return The URI identifying the resource.
     */
    public String getUri(){
    	return uri;
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
    public String getEntityTag(){
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
    public boolean equals(Object object){
        if(object == null || this.getClass() != object.getClass()){ return false; }

        ResourceMetadata resourceMetadata = (ResourceMetadata)object;
        if(
        		(
        				(this.uri == null && resourceMetadata.uri == null) ||
        				this.uri.equals(resourceMetadata.uri)
				) &&
        		(
        				(this.lastModified == null && resourceMetadata.lastModified == null) ||
        				this.lastModified.equals(resourceMetadata.lastModified)
				) &&
        		(
        				(this.entityTag == null && resourceMetadata.entityTag == null) ||
        				this.entityTag.equals(resourceMetadata.entityTag)
				)
            
        ){
            return true;
        }
        return false;
    }

    /**
     * Generates a hashcode representative of the current state of the
     * calling ResourceMetadata instance.
     * @return The hashcode of the calling ResourceMetadata instance.
     */
    @Override
    public int hashCode(){
        int hashCode = 1;
        final int prime = 17;

        if(this.uri != null){
        	hashCode = hashCode * prime + this.uri.hashCode();
        }
        
        if(this.lastModified != null){
        	hashCode = hashCode * prime + this.lastModified.hashCode();
        }
        
        if(this.entityTag != null){
        	hashCode = hashCode * prime + this.entityTag.hashCode();
        }
        
        return hashCode;
    }

    /**
     * Creates a string representation of the calling ResourceMetadata instance.
     * @return A string representation of the calling ResourceMetadata instance.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName() + "\n");
        builder.append("URI\t\t\t\t-->" + this.uri == null ? "null" : this.uri.toString() + "\n");
        builder.append("Last Modified\t\t-->" + this.lastModified == null ? "null" : this.lastModified.toString() + "\n");
        builder.append("Entity Tag\t\t-->" + this.entityTag == null ? "null" : this.entityTag + "\n");
        return builder.toString();
    }
}
