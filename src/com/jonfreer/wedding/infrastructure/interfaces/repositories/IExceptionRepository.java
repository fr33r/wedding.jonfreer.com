package com.jonfreer.wedding.infrastructure.interfaces.repositories;

/**
 * @author jonfreer
 * @since 12/11/16
 */
public interface IExceptionRepository {

    /**
     * Creates a new exception in the exception repository.
     * @param exception The exception to create in the exception repository.
     * @return An identifier for the exception created.
     */
    int create(java.lang.Exception exception);
}
