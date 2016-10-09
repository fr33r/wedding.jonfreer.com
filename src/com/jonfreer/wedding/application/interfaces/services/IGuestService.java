package com.jonfreer.wedding.application.interfaces.services;

import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.application.exceptions.ResourceNotFoundException;
import java.util.ArrayList;

public interface IGuestService{

	ArrayList<Guest> getGuests();

	Guest getGuest(int id) throws ResourceNotFoundException;

	void updateGuest(Guest guest) throws ResourceNotFoundException;

	void deleteGuest(int id) throws ResourceNotFoundException;

	int insertGuest(Guest guest);

}
