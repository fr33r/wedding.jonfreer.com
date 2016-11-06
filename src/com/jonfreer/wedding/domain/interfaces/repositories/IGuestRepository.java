package com.jonfreer.wedding.domain.interfaces.repositories;

import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import java.util.ArrayList;

public interface IGuestRepository{

	ArrayList<Guest> getGuests();
	
	Guest getGuest(int id) throws ResourceNotFoundException;

	void updateGuest(Guest guest) throws ResourceNotFoundException;

	void deleteGuest(int id) throws ResourceNotFoundException;

	int insertGuest(Guest guest);
}
