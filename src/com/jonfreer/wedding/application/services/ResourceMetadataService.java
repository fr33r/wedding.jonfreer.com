package com.jonfreer.wedding.application.services;

import com.jonfreer.wedding.application.interfaces.services.IResourceMetadataService;
import com.jonfreer.wedding.domain.interfaces.repositories.IResourceMetadataRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.servicemodel.metadata.ResourceMetadata;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IResourceMetadataRepositoryFactory;
import org.dozer.Mapper;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.net.URI;

/**
 * @author jonfreer
 * @since 1/5/17
 */
@Service
public class ResourceMetadataService implements IResourceMetadataService {

    private IResourceMetadataRepositoryFactory resourceMetadataRepositoryFactory;
    private IDatabaseUnitOfWorkFactory databaseUnitOfWorkFactory;
    private Mapper mapper;

    /**
     *
     * @param resourceMetadataRepositoryFactory
     * @param databaseUnitOfWorkFactory
     * @param mapper
     */
    @Inject
    public ResourceMetadataService(
        IResourceMetadataRepositoryFactory resourceMetadataRepositoryFactory,
        IDatabaseUnitOfWorkFactory databaseUnitOfWorkFactory,
        Mapper mapper){

        this.resourceMetadataRepositoryFactory = resourceMetadataRepositoryFactory;
        this.databaseUnitOfWorkFactory = databaseUnitOfWorkFactory;
        this.mapper = mapper;
    }

    /**
     * Retrieves resource metadata for a resource identified by
     * the provided URI.
     *
     * @param uri The URI of the resource to retrieve metadata for.
     * @return The resource metadata for the resource identified by
     * the provided URI.
     */
    @Override
    public ResourceMetadata getResourceMetadata(URI uri) {
        try{
            IDatabaseUnitOfWork databaseUnitOfWork =
                this.databaseUnitOfWorkFactory.create();
            IResourceMetadataRepository resourceMetadataRepository =
                this.resourceMetadataRepositoryFactory.create(databaseUnitOfWork);

            com.jonfreer.wedding.domain.metadata.ResourceMetadata resourceMetadata =
                resourceMetadataRepository.getResourceMetadata(uri);

            return this.mapper.map(resourceMetadata, ResourceMetadata.class);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new representation of resource metadata with the
     * provided state.
     *
     * @param resourceMetadata The desired state for the new resource metadata.
     */
    @Override
    public void insertResourceMetadata(ResourceMetadata resourceMetadata) {
        try{
            IDatabaseUnitOfWork databaseUnitOfWork =
                this.databaseUnitOfWorkFactory.create();
            IResourceMetadataRepository resourceMetadataRepository =
                this.resourceMetadataRepositoryFactory.create(databaseUnitOfWork);

            com.jonfreer.wedding.domain.metadata.ResourceMetadata resourceMetadataDomain =
                this.mapper.map(
                    resourceMetadata, com.jonfreer.wedding.domain.metadata.ResourceMetadata.class);

            resourceMetadataRepository.insertResourceMetadata(resourceMetadataDomain);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    /**
     * Replaces the state an existing representation of metadata about a resource
     * with the provided state.
     *
     * @param resourceMetadata The desired state for the resource metadata.
     */
    @Override
    public void updateResourceMetaData(ResourceMetadata resourceMetadata) {
        try{
            IDatabaseUnitOfWork databaseUnitOfWork =
                this.databaseUnitOfWorkFactory.create();
            IResourceMetadataRepository resourceMetadataRepository =
                this.resourceMetadataRepositoryFactory.create(databaseUnitOfWork);

            com.jonfreer.wedding.domain.metadata.ResourceMetadata resourceMetadataDomain =
                this.mapper.map(
                    resourceMetadata, com.jonfreer.wedding.domain.metadata.ResourceMetadata.class);

            resourceMetadataRepository.updateResourceMetaData(resourceMetadataDomain);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    /**
     * Deletes the resource metadata for a resource.
     *
     * @param uri The URI of the resource to delete metadata for.
     */
    @Override
    public void deleteResourceMetaData(URI uri) {
        try{
            IDatabaseUnitOfWork databaseUnitOfWork =
                this.databaseUnitOfWorkFactory.create();
            IResourceMetadataRepository resourceMetadataRepository =
                this.resourceMetadataRepositoryFactory.create(databaseUnitOfWork);
            resourceMetadataRepository.deleteResourceMetaData(uri);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
