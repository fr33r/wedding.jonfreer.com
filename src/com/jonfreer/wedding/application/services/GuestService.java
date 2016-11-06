package com.jonfreer.wedding.application.services;

import java.util.ArrayList;

import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IGuestRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IReservationRepositoryFactory;
import com.jonfreer.wedding.domain.interfaces.repositories.IReservationRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.domain.Reservation;

import com.jonfreer.wedding.domain.Guest;

public class GuestService implements IGuestService{

	private IGuestRepositoryFactory guestRepositoryFactory;
	private IReservationRepositoryFactory reservationRepositoryFactory;
	private IDatabaseUnitOfWorkFactory databaseUnitOfWorkFactory;

	public GuestService(
			IGuestRepositoryFactory guestRepositoryFactory, 
			IReservationRepositoryFactory reservationRepositoryFactory,
			IDatabaseUnitOfWorkFactory databaseUnitOfWorkFactory){
		
		this.guestRepositoryFactory = guestRepositoryFactory;
		this.reservationRepositoryFactory = reservationRepositoryFactory;
		this.databaseUnitOfWorkFactory = databaseUnitOfWorkFactory;
	}

	public Guest getGuest(int id) throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException{
		
		long before = new java.util.Date().getTime();
		IDatabaseUnitOfWork unitOfWork = 
				this.databaseUnitOfWorkFactory.create();
		System.out.println("DatabaseUnitOfWorkFacotry took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
		before = new java.util.Date().getTime();
		IGuestRepository guestRepository = 
				this.guestRepositoryFactory.create(unitOfWork);
		System.out.println("GuestRepositoryFactory took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
		before = new java.util.Date().getTime();
		IReservationRepository reservationRepository = 
				this.reservationRepositoryFactory.create(unitOfWork);
		System.out.println("ReservationRepositoryFactory took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
		
		
		try{

			before = new java.util.Date().getTime();
			Guest guest = guestRepository.getGuest(id);
			System.out.println("GuestRepository.getGuest(id) took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
			
			if(guest.getReservation() != null){
				before = new java.util.Date().getTime();
				Reservation reservation = 
						reservationRepository.getReservation(guest.getReservation().getId());
				guest.setReservation(reservation);
				System.out.println("ReservationRepository.getReservation(id) took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
			}
			
			unitOfWork.Save();
			System.out.println("GuestService.getGuest(id) took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
			return guest;
			
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
			unitOfWork.Undo();
			throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
					resourceNotFoundEx.getMessage(), 
					resourceNotFoundEx, resourceNotFoundEx.getResourceId());
		}
		catch(Exception ex){
			//log.
			unitOfWork.Undo();
			throw new RuntimeException(ex);
		}
	}

	public void updateGuest(Guest guest) throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException{
		
		IDatabaseUnitOfWork unitOfWork = 
				this.databaseUnitOfWorkFactory.create();
		IGuestRepository guestRepository = 
				this.guestRepositoryFactory.create(unitOfWork);
		IReservationRepository reservationRepository = 
				this.reservationRepositoryFactory.create(unitOfWork);
		
		try{
			
			Guest guestCurrentState = guestRepository.getGuest(guest.getId());
		
			/*
			 * 1 - if an id for the reservation is provided:
			 *   a  - make sure it is the same reservation id that already exists.
			 *   b  - overwrite the reservation information.
			 * 2 - if an id for the reservation is not provided:
			 *   a  - check to see if the guest currently has a reservation id.
			 *   b  - if so overwrite the changes to that reservation.
			 *   c  - if not, create a new reservation with the values provided.
			 * 3 - if the guest reservation property is null:
			 *   a  - check to see if the guest currently has a reservation.
			 *   b  - if they do, delete it.
			 */
			if(guest.getReservation() == null){
				if(guestCurrentState.getReservation() != null){
					reservationRepository.deleteReservation(guestCurrentState.getReservation().getId());
				}
			}else if(guest.getReservation().getId() > 0){
				if(guestCurrentState.getReservation() == null || 
						guestCurrentState.getReservation().getId() != guest.getReservation().getId()){
					//throw.
				}else{
					reservationRepository.updateReservation(guest.getReservation());
				}
			}else{
				if(guestCurrentState.getReservation() == null){
					reservationRepository.insertReservation(guest.getReservation());
				}else{
					reservationRepository.updateReservation(guest.getReservation());
				}
			}
			
			guestRepository.updateGuest(guest);
			
			unitOfWork.Save();
			
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
			unitOfWork.Undo();
			throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
					resourceNotFoundEx.getMessage(), 
					resourceNotFoundEx, resourceNotFoundEx.getResourceId());
		}
		catch(Exception ex){
			//log.
			unitOfWork.Undo();
			throw new RuntimeException(ex);
		}
	}

	public void deleteGuest(int id) throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException{
		
		IDatabaseUnitOfWork unitOfWork = 
				this.databaseUnitOfWorkFactory.create();
		IGuestRepository guestRepository = 
				this.guestRepositoryFactory.create(unitOfWork);
		IReservationRepository reservationRepository = 
				this.reservationRepositoryFactory.create(unitOfWork);
		
		try{
			
			Guest guest = guestRepository.getGuest(id);
			if(guest.getReservation() != null){
				reservationRepository.deleteReservation(guest.getReservation().getId());
			}
			
			guestRepository.deleteGuest(id);
			
			unitOfWork.Save();
			
		}catch(ResourceNotFoundException resourceNotFoundEx){
			//log.
			unitOfWork.Undo();
			throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
					resourceNotFoundEx.getMessage(), 
					resourceNotFoundEx, resourceNotFoundEx.getResourceId());
		}
		catch(Exception ex){
			//log.
			unitOfWork.Undo();
			throw new RuntimeException(ex);
		}
	}

	public int insertGuest(Guest guest){
		
		IDatabaseUnitOfWork unitOfWork = 
				this.databaseUnitOfWorkFactory.create();
		IGuestRepository guestRepository = 
				this.guestRepositoryFactory.create(unitOfWork);
		IReservationRepository reservationRepository = 
				this.reservationRepositoryFactory.create(unitOfWork);
		
		try{
			int guestId = guestRepository.insertGuest(guest);
			if(guest.getReservation() != null){
				reservationRepository.insertReservation(guest.getReservation());
			}
			
			unitOfWork.Save();
			
			return guestId;
		}catch(Exception ex){
			//log.
			unitOfWork.Undo();
			throw new RuntimeException(ex);
		}
	}
	
	public ArrayList<Guest> getGuests() {
		
		long before = new java.util.Date().getTime();
		IDatabaseUnitOfWork unitOfWork = 
				this.databaseUnitOfWorkFactory.create();
		System.out.println("DatabaseUnitOfWorkFacotry took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
		before = new java.util.Date().getTime();
		IGuestRepository guestRepository = 
				this.guestRepositoryFactory.create(unitOfWork);
		System.out.println("GuestRepositoryFactory took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
		before = new java.util.Date().getTime();
		IReservationRepository reservationRepository = 
				this.reservationRepositoryFactory.create(unitOfWork);
		System.out.println("ReservationRepositoryFactory took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
		
		try{
			before = new java.util.Date().getTime();
			ArrayList<Guest> guests = guestRepository.getGuests();
			System.out.println("GuestRepository.getGuests took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
			before = new java.util.Date().getTime();
			for(Guest guest : guests){
				if(guest.getReservation() != null){
					guest.setReservation(reservationRepository.getReservation(guest.getReservation().getId()));
				}
			}
			System.out.println("All of the ReservationRepository.getReservation(id) calls took " + (new java.util.Date().getTime() - before) + " milliseconds to complete.");
			
			unitOfWork.Save();
			
			return guests;
		}
		catch(Exception ex){
			//log.
			unitOfWork.Undo();
			throw new RuntimeException(ex);
		}
	}
}