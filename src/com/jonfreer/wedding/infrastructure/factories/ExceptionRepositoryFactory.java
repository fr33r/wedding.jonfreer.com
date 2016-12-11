package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IExceptionRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.repositories.IExceptionRepository;
import com.jonfreer.wedding.infrastructure.repositories.ExceptionRepository;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Named;

/**
 * @author jonfreer
 * @since 12/11/16
 */
@Service
@Named
public class ExceptionRepositoryFactory implements IExceptionRepositoryFactory {

    /**
     *
     * @param unitOfWork
     * @return
     */
    @Override
    public IExceptionRepository create(IDatabaseUnitOfWork unitOfWork) {
        return new ExceptionRepository(unitOfWork);
    }
}
