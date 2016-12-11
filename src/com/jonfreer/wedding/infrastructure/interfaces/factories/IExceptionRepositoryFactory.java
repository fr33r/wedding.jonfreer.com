package com.jonfreer.wedding.infrastructure.interfaces.factories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.repositories.IExceptionRepository;
import org.jvnet.hk2.annotations.Contract;

/**
 * @author jonfreer
 * @since 12/11/16
 */
@Contract
public interface IExceptionRepositoryFactory {

    /**
     *
     * @param unitOfWork
     * @return
     */
    IExceptionRepository create(IDatabaseUnitOfWork unitOfWork);
}
