package com.jonfreer.wedding.infrastructure.interfaces.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IResourceMetadataRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

/**
 * Represents the contract that is to be implemented by any class that
 * wishes to serve as a factory for instances implementing IResourceMetadataRepository.
 * @author jonfreer
 * @since 1/5/17
 */
public interface IResourceMetadataRepositoryFactory {

    /**
     * Creates an instance of a class that implements the IResourceMetadataRepository interface.
     *
     * @param unitOfWork The instance of IDatabaseUnitOfWork needed to create a new instance
     *                   of a class implementing the IResourceMetadataRepository interface.
     * @return The instance of a class that implements the IResourceMetadataRepository interface.
     */
    IResourceMetadataRepository create(IDatabaseUnitOfWork unitOfWork);
}
