package com.jonfreer.wedding.domain.interfaces.unitofwork;

/**
 * Defines the contract for any class or interface that wishes
 * to represent a unit of work.
 */
public interface IUnitOfWork {

    /**
     * Persists the unit of work.
     */
    void Save();

    /**
     * Discards the unit of work.
     */
    void Undo();

}
