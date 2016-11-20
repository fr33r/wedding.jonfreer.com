package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IReservationRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IReservationRepositoryFactory;
import com.jonfreer.wedding.infrastructure.repositories.ReservationRepository;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Named;

/**
 * Factory that creates ReservationRepository instances.
 */
@Service
@Named
public class ReservationRepositoryFactory implements IReservationRepositoryFactory {

    /**
     * Creates a new instance of ReservationRepository, provided an instance of a class that
     * implements the IDatabaseUnitOfWork interface.
     *
     * @param unitOfWork The instance of IDatabaseUnitOfWork needed to create a new instance
     *                   of a class implementing the IReservationRepository interface.
     * @return A new instance of ReservationRepository.
     */
    @Override
    public IReservationRepository create(IDatabaseUnitOfWork unitOfWork) {
        return new ReservationRepository(unitOfWork);
    }

}
