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

	public Guest getGuest(int id) throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException{
		try{
			return this.guestRepository.getGuest(id);
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
			throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
					resourceNotFoundEx.getMessage(), 
					resourceNotFoundEx, resourceNotFoundEx.getResourceId());
		}
		catch(Exception ex){
			//log.
			throw new RuntimeException(ex);
		}
	}

	public void updateGuest(Guest guest) throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException{
		try{
			this.guestRepository.updateGuest(guest);
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
			throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
					resourceNotFoundEx.getMessage(), 
					resourceNotFoundEx, resourceNotFoundEx.getResourceId());
		}
		catch(Exception ex){
			//log.
			throw new RuntimeException(ex);
		}
	}

	public void deleteGuest(int id) throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException{
		try{
			this.guestRepository.deleteGuest(id);
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
			throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
					resourceNotFoundEx.getMessage(), 
					resourceNotFoundEx, resourceNotFoundEx.getResourceId());
		}
		catch(Exception ex){
			//log.
			throw new RuntimeException(ex);
		}
	}

	public int insertGuest(Guest guest){
		try{
			return this.guestRepository.insertGuest(guest);
		}catch(Exception ex){
			//log.
			throw new RuntimeException(ex);
		}
	}
}
