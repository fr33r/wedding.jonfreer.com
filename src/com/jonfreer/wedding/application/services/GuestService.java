package com.jonfreer.wedding.application.services;

import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.domain.Guest;

public class GuestService implements IGuestService{

	private IGuestRepository guestRepository;

	public GuestService(IGuestRepository guestRepository){
		this.guestRepository = guestRepository;
	}

	public Guest getGuest(int id){
		try{
			return this.guestRepository.getGuest(id);
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
		}
		catch(Exception ex){
			//log.
		}
	}

	public void updateGuest(Guest guest){
		try{
			this.guestRepository.updateGuest(guest);
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
		}
		catch(Exception ex){
			//log.
		}
	}

	public void deleteGuest(int id){
		try{
			this.guestRepository.deleteGuest(id);
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
		}
		catch(Exception ex){
			//log.
		}
	}

	public int insertGuest(Guest guest){
		try{
			return this.guestRepository.insertGuest(guest);
		}catch(Exception ex){
			//log.
		}
	}
}
