package com.jonfreer.wedding.application.services;

import java.util.ArrayList;

import com.jonfreer.wedding.infrastructure.interfaces.services.LogService;
import com.jonfreer.wedding.servicemodel.GuestSearchCriteria;
import org.dozer.Mapper;
import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IGuestRepositoryFactory;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IReservationRepositoryFactory;
import com.jonfreer.wedding.domain.interfaces.repositories.IReservationRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.domain.Reservation;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Named;
import javax.inject.Inject;

@Service
@Named
public class GuestService implements IGuestService {

    private final IGuestRepositoryFactory guestRepositoryFactory;
    private final IReservationRepositoryFactory reservationRepositoryFactory;
    private final IDatabaseUnitOfWorkFactory databaseUnitOfWorkFactory;
    private final LogService logService;
    private final Mapper mapper;

    @Inject
    public GuestService(
            IGuestRepositoryFactory guestRepositoryFactory,
            IReservationRepositoryFactory reservationRepositoryFactory,
            IDatabaseUnitOfWorkFactory databaseUnitOfWorkFactory,
            LogService logService,
            Mapper mapper) {

        this.guestRepositoryFactory = guestRepositoryFactory;
        this.reservationRepositoryFactory = reservationRepositoryFactory;
        this.databaseUnitOfWorkFactory = databaseUnitOfWorkFactory;
        this.logService = logService;
        this.mapper = mapper;
    }

    public com.jonfreer.wedding.servicemodel.Guest getGuest(int id)
            throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException {

        IDatabaseUnitOfWork unitOfWork =
                this.databaseUnitOfWorkFactory.create();
        IGuestRepository guestRepository =
                this.guestRepositoryFactory.create(unitOfWork);
        IReservationRepository reservationRepository =
                this.reservationRepositoryFactory.create(unitOfWork);

        try {

            com.jonfreer.wedding.domain.Guest guest = guestRepository.getGuest(id);

            if (guest.getReservation() != null) {
                Reservation reservation =
                        reservationRepository.getReservation(guest.getReservation().getId());
                guest.setReservation(reservation);
            }

            unitOfWork.Save();

            return this.mapper.map(guest, com.jonfreer.wedding.servicemodel.Guest.class);

        } catch (ResourceNotFoundException resourceNotFoundEx) {
            unitOfWork.Undo();
            this.logService.info(resourceNotFoundEx.getLocalizedMessage());
            throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
                    resourceNotFoundEx.getMessage(),
                    resourceNotFoundEx, resourceNotFoundEx.getResourceId());
        } catch (Exception ex) {
            unitOfWork.Undo();
            this.logService.error(ex);
            throw new RuntimeException(ex);
        }
    }

    public void updateGuest(com.jonfreer.wedding.servicemodel.Guest guest)
            throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException {

        IDatabaseUnitOfWork unitOfWork =
                this.databaseUnitOfWorkFactory.create();
        IGuestRepository guestRepository =
                this.guestRepositoryFactory.create(unitOfWork);
        IReservationRepository reservationRepository =
                this.reservationRepositoryFactory.create(unitOfWork);

        try {

            com.jonfreer.wedding.domain.Guest guestDomain =
                    this.mapper.map(guest, com.jonfreer.wedding.domain.Guest.class);
            com.jonfreer.wedding.domain.Guest guestCurrentState = guestRepository.getGuest(guest.getId());

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
            if (guest.getReservation() == null) {
                if (guestCurrentState.getReservation() != null) {
                    reservationRepository.deleteReservation(guestCurrentState.getReservation().getId());
                }
            } else if (guest.getReservation().getId() != null) {
                if (guestCurrentState.getReservation() == null ||
                        guestCurrentState.getReservation().getId() != guest.getReservation().getId()) {
                    //throw.
                } else {
                    reservationRepository.updateReservation(guestDomain.getReservation());
                }
            } else {
                if (guestCurrentState.getReservation() == null) {
                    int id = reservationRepository.insertReservation(guestDomain.getReservation());
                    guestDomain.getReservation().setId(id);
                } else {
                    reservationRepository.updateReservation(guestDomain.getReservation());
                }
            }

            guestRepository.updateGuest(guestDomain);

            unitOfWork.Save();

        } catch (ResourceNotFoundException resourceNotFoundEx) {
            unitOfWork.Undo();
            this.logService.info(resourceNotFoundEx.getLocalizedMessage());
            throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
                    resourceNotFoundEx.getMessage(),
                    resourceNotFoundEx, resourceNotFoundEx.getResourceId());
        } catch (Exception ex) {
            unitOfWork.Undo();
            this.logService.error(ex);
            throw new RuntimeException(ex);
        }
    }

    public void deleteGuest(int id)
            throws com.jonfreer.wedding.application.exceptions.ResourceNotFoundException {

        IDatabaseUnitOfWork unitOfWork =
                this.databaseUnitOfWorkFactory.create();
        IGuestRepository guestRepository =
                this.guestRepositoryFactory.create(unitOfWork);
        IReservationRepository reservationRepository =
                this.reservationRepositoryFactory.create(unitOfWork);

        try {

            com.jonfreer.wedding.domain.Guest guest = guestRepository.getGuest(id);
            if (guest.getReservation() != null) {
                reservationRepository.deleteReservation(guest.getReservation().getId());
            }

            guestRepository.deleteGuest(id);

            unitOfWork.Save();

        } catch (ResourceNotFoundException resourceNotFoundEx) {
            unitOfWork.Undo();
            this.logService.info(resourceNotFoundEx.getLocalizedMessage());
            throw new com.jonfreer.wedding.application.exceptions.ResourceNotFoundException(
                    resourceNotFoundEx.getMessage(),
                    resourceNotFoundEx, resourceNotFoundEx.getResourceId());
        } catch (Exception ex) {
            unitOfWork.Undo();
            this.logService.error(ex);
            throw new RuntimeException(ex);
        }
    }

    public int insertGuest(com.jonfreer.wedding.servicemodel.Guest guest) {

        IDatabaseUnitOfWork unitOfWork =
                this.databaseUnitOfWorkFactory.create();
        IGuestRepository guestRepository =
                this.guestRepositoryFactory.create(unitOfWork);
        IReservationRepository reservationRepository =
                this.reservationRepositoryFactory.create(unitOfWork);

        try {
            com.jonfreer.wedding.domain.Guest guestDomain =
                this.mapper.map(guest, com.jonfreer.wedding.domain.Guest.class);
            
            if (guestDomain.getReservation() != null && guestDomain.getReservation().getId() == null) {
                int reservationId = 
            		reservationRepository.insertReservation(guestDomain.getReservation());
                guestDomain.getReservation().setId(reservationId);
            }
            
            int guestId = guestRepository.insertGuest(guestDomain);

            unitOfWork.Save();

            return guestId;
        } catch (Exception ex) {
            unitOfWork.Undo();
            this.logService.error(ex);
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<com.jonfreer.wedding.servicemodel.Guest> getGuests(GuestSearchCriteria searchCriteria) {

        IDatabaseUnitOfWork unitOfWork =
            this.databaseUnitOfWorkFactory.create();
        IGuestRepository guestRepository =
            this.guestRepositoryFactory.create(unitOfWork);
        IReservationRepository reservationRepository =
            this.reservationRepositoryFactory.create(unitOfWork);

        try {
            com.jonfreer.wedding.domain.GuestSearchCriteria searchCriteriaDomain = null;
            if(searchCriteria != null){
            	searchCriteriaDomain = 
        			this.mapper.map(searchCriteria, com.jonfreer.wedding.domain.GuestSearchCriteria.class);
            }
            ArrayList<com.jonfreer.wedding.domain.Guest> guests =
                guestRepository.getGuests(searchCriteriaDomain);

            for (com.jonfreer.wedding.domain.Guest guest : guests) {
                if (guest.getReservation() != null) {
                    guest.setReservation(reservationRepository.getReservation(guest.getReservation().getId()));
                }
            }

            unitOfWork.Save();

            ArrayList<com.jonfreer.wedding.servicemodel.Guest> guestsServiceModel = 
            		new ArrayList<com.jonfreer.wedding.servicemodel.Guest>();
            for(com.jonfreer.wedding.domain.Guest guest : guests){
            	guestsServiceModel.add(this.mapper.map(guest, com.jonfreer.wedding.servicemodel.Guest.class));
            }
            
            return guestsServiceModel;
        } catch (Exception ex) {
            unitOfWork.Undo();
            this.logService.error(ex);
            throw new RuntimeException(ex);
        }
    }
}
