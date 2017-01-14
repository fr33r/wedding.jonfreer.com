package com.jonfreer.wedding.application.interfaces.services;

import com.jonfreer.wedding.servicemodel.metadata.ResourceMetadata;
import org.jvnet.hk2.annotations.Contract;

import java.net.URI;

/**
 * @author jonfreer
 * @since 1/5/17
 */
@Contract
public interface IResourceMetadataService {

    /**
     * Retrieves resource metadata for a resource identified by
     * the provided URI.
     * @param uri The URI of the resource to retrieve metadata for.
     * @return The resource metadata for the resource identified by
     * the provided URI.
     */
    ResourceMetadata getResourceMetadata(URI uri);

    /**
     * Creates a new representation of resource metadata with the
     * provided state.
     * @param resourceMetadata The desired state for the new resource metadata.
     */
    void insertResourceMetadata(ResourceMetadata resourceMetadata);

    /**
     * Replaces the state an existing representation of metadata about a resource
     * with the provided state.
     * @param resourceMetadata The desired state for the resource metadata.
     */
    void updateResourceMetaData(ResourceMetadata resourceMetadata);

    /**
     * Deletes the resource metadata for a resource.
     * @param uri The URI of the resource to delete metadata for.
     */
    void deleteResourceMetaData(URI uri);
}
