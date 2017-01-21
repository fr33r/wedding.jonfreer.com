package com.jonfreer.wedding.infrastructure.repositories;

import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.domain.GuestSearchCriteria;
import com.jonfreer.wedding.domain.Reservation;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import javax.inject.Named;

import org.jvnet.hk2.annotations.Service;

/**
 * A database repository that directly interacts with the database
 * to manage guest entities.
 */
@Service
@Named
public class GuestRepository extends DatabaseRepository implements IGuestRepository {

    /**
     * Constructs a new instance provided an instance of a class that
     * implements the IDatabaseUnitOfWork interface. It is recommended
     * that instead of invoking this constructor, instead use the
     * GuestRepositoryFactory class to create an instance.
     *
     * @param unitOfWork An instance of a class that implements the
     *                   IDatabaseUnitOfWork interface. All methods invoked
     *                   on the GuestRepository instance being created will
     *                   utilize this unit of work.
     */
    public GuestRepository(IDatabaseUnitOfWork unitOfWork) {
        super(unitOfWork);
    }

    /**
     * Retrieves a guest that is identified by the identifier provided.
     *
     * @param id The identifier of the guest to be retrieved.
     * @return An instance of Guest that has the identifier specified.
     * @throws ResourceNotFoundException Thrown when a guest with the identifier
     *                                   provided cannot be found.
     */
    public Guest getGuest(int id) throws ResourceNotFoundException {

        Guest guest = null;
        CallableStatement cStatement = null;
        ResultSet result = null;

        try {
            cStatement =
                this.getUnitOfWork().createCallableStatement("{CALL GetGuest(?)}");

            cStatement.setInt(1, id);
            result = cStatement.executeQuery();

            if (result.next()) {
                guest = new Guest();
                guest.setId(result.getInt("GUEST_ID"));
                guest.setGivenName(result.getString("FIRST_NAME"));
                guest.setSurName(result.getString("LAST_NAME"));
                guest.setDescription(result.getString("GUEST_DESCRIPTION"));
                guest.setDietaryRestrictions(result.getString("GUEST_DIETARY_RESTRICTIONS"));
                guest.setInviteCode(result.getString("INVITE_CODE"));

                int reservationId = result.getInt("RESERVATION_ID");
                if (!result.wasNull()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(reservationId);
                    guest.setReservation(reservation);
                }
            }

            if (guest == null) {
                throw new ResourceNotFoundException(
                    "A guest with an ID of '" + id + "' could not be found.", id);
            }

            return guest;

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException(sqlEx);
        } finally {
            //release resources needed.
            try {
                if (cStatement != null && !cStatement.isClosed()) {
                    cStatement.close();
                }
                if (result != null && !result.isClosed()) {
                    result.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                throw new RuntimeException(sqlEx);
            }
        }
    }

    /**
     * Replaces the state of an existing guest with the state of the guest provided.
     *
     * @param guest The desired state of the guest to update.
     * @throws ResourceNotFoundException Thrown when a guest with the identifier
     *                                   provided in the desired state cannot be found.
     */
    public void updateGuest(Guest guest) throws ResourceNotFoundException {

        CallableStatement cStatement = null;

        try {
            cStatement =
                this.getUnitOfWork().createCallableStatement(
                    "{CALL UpdateGuest(?, ?, ?, ?, ?, ?, ?)}"
                );
            cStatement.setInt(1, guest.getId());
            cStatement.setString(2, guest.getGivenName());
            cStatement.setString(3, guest.getSurName());
            cStatement.setString(4, guest.getDescription());
            cStatement.setString(5, guest.getDietaryRestrictions());
            cStatement.setString(6, guest.getInviteCode());

            if (guest.getReservation() == null) {
                cStatement.setNull(7, java.sql.Types.INTEGER);
            } else {
                cStatement.setInt(7, guest.getReservation().getId());
            }
            
            int numOfRecords = cStatement.executeUpdate();

            if (numOfRecords < 1) {
                throw new ResourceNotFoundException(
                    "A guest with an ID of '" + guest.getId() + "' could not be found.", guest.getId());
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException(sqlEx);
        } finally {
            //release resources needed.
            try {
                if (cStatement != null) {
                    cStatement.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                throw new RuntimeException(sqlEx);
            }
        }
    }

    /**
     * Deletes a guest that is identified by the identifier provided.
     *
     * @param id The identifier of the guest to be deleted.
     * @throws ResourceNotFoundException Thrown when a guest with the identifier
     *                                   provided cannot be found.
     */
    public void deleteGuest(int id) throws ResourceNotFoundException {

        CallableStatement cStatement = null;

        try {
            cStatement = this.getUnitOfWork().createCallableStatement("{CALL DeleteGuest(?)}");
            cStatement.setInt(1, id);

            int numOfRecords = cStatement.executeUpdate();

            if (numOfRecords < 1) {
                throw new ResourceNotFoundException(
                    "A guest with an ID of '" + id + "' could not be found.", id);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException(sqlEx);
        } finally {
            //release resources needed.
            try {
                if (cStatement != null) {
                    cStatement.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                throw new RuntimeException(sqlEx);
            }
        }
    }

    /**
     * Creates a new guest with the state provided.
     *
     * @param guest The desired state of the guest to create.
     * @return The identifier of the newly created guest.
     */
    public int insertGuest(Guest guest) {

        CallableStatement cStatement = null;
        ResultSet result = null;

        try {
            cStatement =
                this.getUnitOfWork().createCallableStatement(
                    "{CALL CreateGuest(?, ?, ?, ?, ?, ?, ?)}"
                );
            cStatement.setString(1, guest.getGivenName());
            cStatement.setString(2, guest.getSurName());
            cStatement.setString(3, guest.getDescription());
            cStatement.setString(4, guest.getDietaryRestrictions());
            cStatement.setString(5, guest.getInviteCode());

            if (guest.getReservation() == null) {
                cStatement.setNull(6, java.sql.Types.INTEGER);
            } else {
                cStatement.setInt(6, guest.getReservation().getId());
            }

            cStatement.registerOutParameter("Id", Types.INTEGER);

            cStatement.executeUpdate();

            return cStatement.getInt("Id");

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException(sqlEx);
        } finally {

            //release resources needed.
            try {
                if (cStatement != null) {
                    cStatement.close();
                }
                if (result != null) {
                    result.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                throw new RuntimeException(sqlEx);
            }
        }
    }

    /**
     * Retrieves all of the guests matching the provided search criteria.
     * The search criteria is optional, and when omitted, all guests are returned.
     * @param searchCriteria The search criteria that is used to filter the guests
     *                       in the repository.
     * @return A collection of guests that match the search criteria if provided;
     * otherwise, a collection of all the guests in the repository.
     */
    public ArrayList<Guest> getGuests(GuestSearchCriteria searchCriteria) {

        ArrayList<Guest> guests = new ArrayList<Guest>();
        CallableStatement cStatement = null;
        ResultSet result = null;

        try {
            cStatement = this.getUnitOfWork().createCallableStatement("{CALL GetGuests(?, ?, ?)}");

            if(searchCriteria != null){
                cStatement.setString(1, searchCriteria.getInviteCode());
                cStatement.setString(2, searchCriteria.getGivenName());
                cStatement.setString(3, searchCriteria.getSurname());
            }else{
                cStatement.setString(1, null);
                cStatement.setString(2, null);
                cStatement.setString(3, null);
            }

            result = cStatement.executeQuery();

            while (result.next()) {
                Guest guest = new Guest();
                guest.setId(result.getInt("GUEST_ID"));
                guest.setGivenName(result.getString("FIRST_NAME"));
                guest.setSurName(result.getString("LAST_NAME"));
                guest.setDescription(result.getString("GUEST_DESCRIPTION"));
                guest.setDietaryRestrictions(result.getString("GUEST_DIETARY_RESTRICTIONS"));
                guest.setInviteCode(result.getString("INVITE_CODE"));

                int reservationId = result.getInt("RESERVATION_ID");
                if (!result.wasNull()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(reservationId);
                    guest.setReservation(reservation);
                }

                guests.add(guest);
            }

            return guests;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException(sqlEx);
        } finally {
            //release resources needed.
            try {
                if (cStatement != null && !cStatement.isClosed()) {
                    cStatement.close();
                }
                if (result != null && !result.isClosed()) {
                    result.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                throw new RuntimeException(sqlEx);
            }
        }
    }
}
