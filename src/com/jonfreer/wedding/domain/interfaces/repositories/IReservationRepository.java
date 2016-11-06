package com.jonfreer.wedding.domain.interfaces.repositories;

import com.jonfreer.wedding.domain.Reservation;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;

public interface IReservationRepository {

	Reservation getReservation(int id) throws ResourceNotFoundException;
	
	void updateReservation(Reservation desiredReservationState) throws ResourceNotFoundException;
	
	int insertReservation(Reservation desiredReservationState);
	
	void deleteReservation(int id) throws ResourceNotFoundException;	
}
