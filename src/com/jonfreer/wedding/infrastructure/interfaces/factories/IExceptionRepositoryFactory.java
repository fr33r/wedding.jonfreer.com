package com.jonfreer.wedding.infrastructure.interfaces.factories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.repositories.IExceptionRepository;
import org.jvnet.hk2.annotations.Contract;

/**
 * Represents the contract that is to be implemented by any class or interface that
 * wishes to serve as a factory for instances implementing IExceptionRepository.
 *
 * @author jonfreer
 * @since 12/11/16
 */
@Contract
public interface IExceptionRepositoryFactory {

    /**
     * Creates an instance of a class that implements the IExceptionRepository interface.
     *
     * @param unitOfWork The instance of IDatabaseUnitOfWork needed to create a new instance
     *                   of a class implementing the IExceptionRepository interface.
     * @return The instance of a class that implements the IExceptionRepository interface.
     */
    IExceptionRepository create(IDatabaseUnitOfWork unitOfWork);
}
