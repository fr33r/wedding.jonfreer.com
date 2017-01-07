package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IResourceMetadataRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IResourceMetadataRepositoryFactory;
import com.jonfreer.wedding.infrastructure.repositories.ResourceMetadataRepository;

/**
 * @author jonfreer
 * @since 1/5/17
 */
public class ResourceMetadataRepositoryFactory implements IResourceMetadataRepositoryFactory {

    /**
     * Creates an instance of a class that implements the IResourceMetadataRepository interface.
     *
     * @param unitOfWork The instance of IDatabaseUnitOfWork needed to create a new instance
     *                   of a class implementing the IResourceMetadataRepository interface.
     * @return The instance of a class that implements the IResourceMetadataRepository interface.
     */
    @Override
    public IResourceMetadataRepository create(IDatabaseUnitOfWork unitOfWork) {
        return new ResourceMetadataRepository(unitOfWork);
    }
}
