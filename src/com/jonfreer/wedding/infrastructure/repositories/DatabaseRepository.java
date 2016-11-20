package com.jonfreer.wedding.infrastructure.repositories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

/**
 * An abstract class representing a repository that interacts with a database.
 */
public abstract class DatabaseRepository {

    private IDatabaseUnitOfWork unitOfWork;

    /**
     * Constructs a DatabaseRepository instance, provided an instance of a class
     * that implements the IDatabaseUnitOfWork interface.
     *
     * @param unitOfWork The instance of IDatabaseUnitOfWork needed to create a new instance
     *                   of any class inheriting from this class.
     */
    public DatabaseRepository(IDatabaseUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    /**
     * Retrieves the unit of work for the repository.
     *
     * @return The unit of work for the repository.
     */
    public IDatabaseUnitOfWork getUnitOfWork() {
        return this.unitOfWork;
    }
}
