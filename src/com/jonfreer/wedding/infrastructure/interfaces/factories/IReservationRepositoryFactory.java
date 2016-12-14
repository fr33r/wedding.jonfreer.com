package com.jonfreer.wedding.infrastructure.interfaces.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IReservationRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import org.jvnet.hk2.annotations.Contract;

/**
 * Represents the contract that is to be implemented by any class that
 * wishes to serve as a factory for instances implementing IReservationRepository.
 */
@Contract
public interface IReservationRepositoryFactory {

    /**
     * Creates an instance of a class that implements the IGuestRepository interface.
     *
     * @param unitOfWork The instance of IDatabaseUnitOfWork needed to create a new instance
     *                   of a class implementing the IReservationRepository interface.
     * @return The instance of a class that implements the IReservationRepository interface.
     */
    IReservationRepository create(IDatabaseUnitOfWork unitOfWork);
}
