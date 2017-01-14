package com.jonfreer.wedding.domain.interfaces.repositories;

import com.jonfreer.wedding.domain.metadata.ResourceMetadata;

import java.net.URI;

/**
 * Defines the contract for all implementing types
 * that wish to serve as a repository for ResourceMetadata
 * instances.
 * @author jonfreer
 * @since 1/4/17
 */
public interface IResourceMetadataRepository {

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
