package com.jonfreer.wedding.domain.interfaces.repositories;

import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;

import java.util.ArrayList;

import org.jvnet.hk2.annotations.Contract;

/**
 * Defines the contract for any implementing class or interface that
 * wishes to serve as a repository for Guest instances.
 */
@Contract
public interface IGuestRepository {

    /**
     * Retrieves all guests from the repository.
     * @return A collection of Guest instances in the repository.
     */
    ArrayList<Guest> getGuests();

    /**
     * Retrieves a specific guest from the repository.
     * @param id The identifier of the guest to be retrieved.
     * @return The Guest instance that has the identifier specified.
     * @throws ResourceNotFoundException Thrown when the identifier provided
     * does not belong to any Guest instance within the repository.
     */
    Guest getGuest(int id) throws ResourceNotFoundException;

    /**
     * Replaces the current state of a guest with the Guest instance provided.
     * @param guest The desired state for the existing guest.
     * @throws ResourceNotFoundException Thrown when the Guest instance provided
     * does not match any Guest instance within the repository.
     */
    void updateGuest(Guest guest) throws ResourceNotFoundException;

    /**
     * Deletes a guest from the repository.
     * @param id The identifier of the guest to be deleted.
     * @throws ResourceNotFoundException Thrown when the identifier provided
     * does not belong to any Guest instance within the repository.
     */
    void deleteGuest(int id) throws ResourceNotFoundException;

    /**
     * Creates a new guest in the repository.
     * @param guest The desired state of the guest to be created.
     * @return The identifier of the newly created guest.
     */
    int insertGuest(Guest guest);
}
