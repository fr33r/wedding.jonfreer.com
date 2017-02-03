package com.jonfreer.wedding.infrastructure.interfaces.factories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import org.jvnet.hk2.annotations.Contract;

/**
 * Represents the contract that is to be implemented by any class that
 * wishes to serve as a factory for instances implementing IDatabaseUnitOfWork.
 */
@Contract
public interface IDatabaseUnitOfWorkFactory {

    /**
     * Creates an instance of a class that implements the IDatabaseUnitOfWork interface.
     *
     * @return The instance of a class that implements the IDatabaseUnitOfWork interface.
     */
    IDatabaseUnitOfWork create();
}
