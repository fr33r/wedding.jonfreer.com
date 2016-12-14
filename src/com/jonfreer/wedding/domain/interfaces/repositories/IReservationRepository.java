package com.jonfreer.wedding.domain.interfaces.repositories;

import com.jonfreer.wedding.domain.Reservation;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import org.jvnet.hk2.annotations.Contract;

/**
 * Defines the contract for any implementing class or interface that
 * wishes to serve as a repository for Reservation instances.
 */
@Contract
public interface IReservationRepository {

    /**
     * Retrieves a specific reservation from the repository.
     * @param id The identifier of the reservation to be retrieved.
     * @return The Reservation instance that has the identifier specified.
     * @throws ResourceNotFoundException Thrown when the identifier provided
     * does not belong to any reservation within the repository.
     */
    Reservation getReservation(int id) throws ResourceNotFoundException;

    /**
     * Replaces the current state of a reservation with the Reservation instance provided.
     * @param desiredReservationState The desired state for the existing reservation.
     * @throws ResourceNotFoundException Thrown when the Reservation instance provided
     * does not match any Guest instance within the repository.
     */
    void updateReservation(Reservation desiredReservationState) throws ResourceNotFoundException;

    /**
     * Creates a new reservation in the repository.
     * @param desiredReservationState The desired state of the reservation to be created.
     * @return The identifier of the newly created reservation.
     */
    int insertReservation(Reservation desiredReservationState);

    /**
     * Deletes a reservation from the repository.
     * @param id The identifier of the reservation to be deleted.
     * @throws ResourceNotFoundException Thrown when the identifier provided
     * does not belong to any reservation within the repository.
     */
    void deleteReservation(int id) throws ResourceNotFoundException;
}
