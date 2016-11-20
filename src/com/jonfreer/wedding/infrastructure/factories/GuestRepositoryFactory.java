package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IGuestRepositoryFactory;
import com.jonfreer.wedding.infrastructure.repositories.GuestRepository;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Named;

/**
 * Factory that creates GuestRepository instances.
 */
@Service
@Named
public class GuestRepositoryFactory implements IGuestRepositoryFactory {

    /**
     * Creates a new instance of GuestRepository, provided an instance of a class
     * that implements the IDatabaseUnitOfWork interface.
     *
     * @param unitOfWork The instance of IDatabaseUnitOfWork needed to create a new instance
     *                   of a class implementing the IGuestRepository interface.
     * @return A new instance of GuestRepository.
     */
    @Override
    public IGuestRepository create(IDatabaseUnitOfWork unitOfWork) {
        return new GuestRepository(unitOfWork);
    }

}
