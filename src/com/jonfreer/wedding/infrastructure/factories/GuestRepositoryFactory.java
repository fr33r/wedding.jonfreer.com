package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IGuestRepositoryFactory;
import com.jonfreer.wedding.infrastructure.repositories.GuestRepository;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Named;

@Service
@Named
public class GuestRepositoryFactory implements IGuestRepositoryFactory {

    @Override
    public IGuestRepository create(IDatabaseUnitOfWork unitOfWork) {
        return new GuestRepository(unitOfWork);
    }

}
