package com.jonfreer.wedding.domain.metadata;

import java.net.URI;
import java.util.Date;

/**
 * Represents metadata about a single resource.
 * @author jonfreer
 * @since 1/4/17
 */
public class ResourceMetadata {

    private URI uri;
    private Date lastModified;
    private String entityTag;

    /**
     * Constructs a ResourceMetadata instance, provided a URI,
     * last modified date and time, and an entity tag (also referred to as an ETag).
     * @param uri The URI of the resource.
     * @param lastModified The date and time that the resource identified by the URI
     *                     was modified.
     * @param entityTag The entity tag of the resource identified by the URI.
     */
    public ResourceMetadata(URI uri, Date lastModified, String entityTag){

        if(uri == null) {
            throw new IllegalArgumentException("The constructor argument 'uri' cannot be null.");
        }

        if(lastModified == null){
            throw new IllegalArgumentException("The constructor argument 'lastModified' cannot be null.");
        }

        if(entityTag == null){
            throw new IllegalArgumentException("The constructor argument 'entityTag' cannot be null.");
        }

        this.uri = URI.create(uri.toString());
        this.lastModified = (Date)lastModified.clone();
        this.entityTag = entityTag;
    }

    /**
     * Retrieves the URI identifying the resource.
     * @return The URI identifying the resource.
     */
    public URI getUri(){
        return URI.create(this.uri.toString());
    }

    /**
     * Retrieves the date and time that the resource
     * was last modified.
     * @return The date and time that the resource was
     * last modified.
     */
    public Date getLastModified(){
        return (Date)this.lastModified.clone();
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
            this.uri.equals(resourceMetadata.uri) &&
            this.lastModified.equals(resourceMetadata.lastModified) &&
            this.entityTag.equals(resourceMetadata.entityTag)
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

        hashCode = hashCode * prime + this.uri.hashCode();
        hashCode = hashCode * prime + this.lastModified.hashCode();
        hashCode = hashCode * prime + this.entityTag.hashCode();

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
        builder.append("URI\t\t\t\t-->" + this.uri.toString() + "\n");
        builder.append("Last Modified\t\t-->" + this.lastModified.toString() + "\n");
        builder.append("Entity Tag\t\t-->" + this.entityTag + "\n");
        return builder.toString();
    }
}
