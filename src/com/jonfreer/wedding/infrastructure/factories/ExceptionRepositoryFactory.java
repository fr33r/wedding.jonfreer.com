package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IExceptionRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.repositories.IExceptionRepository;
import com.jonfreer.wedding.infrastructure.repositories.ExceptionRepository;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Named;

/**
 * Factory that creates ExceptionRepository instances.
 * @author jonfreer
 * @since 12/11/16
 */
@Service
@Named
public class ExceptionRepositoryFactory implements IExceptionRepositoryFactory {

    /**
     * Creates a new instance of ExceptionRepository, provided an instance of a class
     * that implements the IDatabaseUnitOfWork interface.
     *
     * @param unitOfWork The instance of IDatabaseUnitOfWork needed to create a new instance
     *                   of a class implementing the IExceptionRepository interface.
     * @return A new instance of ExceptionRepository.
     */
    @Override
    public IExceptionRepository create(IDatabaseUnitOfWork unitOfWork) {
        return new ExceptionRepository(unitOfWork);
    }
}
