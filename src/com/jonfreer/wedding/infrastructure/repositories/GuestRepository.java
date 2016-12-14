package com.jonfreer.wedding.infrastructure.repositories;

import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.domain.Reservation;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
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
        PreparedStatement pStatement = null;
        ResultSet result = null;

        try {
            pStatement = this.getUnitOfWork().createPreparedStatement(
                    "SELECT "
                            + "G.GUEST_ID,"
                            + "G.FIRST_NAME,"
                            + "G.LAST_NAME,"
                            + "G.GUEST_DESCRIPTION,"
                            + "G.GUEST_DIETARY_RESTRICTIONS,"
                            + "G.INVITE_CODE,"
                            + "G.RESERVATION_ID"
                            + " FROM "
                            + "wedding_jonfreer_com.GUEST AS G"
                            + " WHERE "
                            + "G.GUEST_ID = ?;");

            pStatement.setInt(1, id);
            result = pStatement.executeQuery();

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
                if (pStatement != null) {
                    pStatement.close();
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
     * Replaces the state of an existing guest with the state of the guest provided.
     *
     * @param guest The desired state of the guest to update.
     * @throws ResourceNotFoundException Thrown when a guest with the identifier
     *                                   provided in the desired state cannot be found.
     */
    public void updateGuest(Guest guest) throws ResourceNotFoundException {

        PreparedStatement pStatement = null;

        try {
            pStatement = this.getUnitOfWork().createPreparedStatement(
                    "UPDATE wedding_jonfreer_com.GUEST"
                            + " SET "
                            + "FIRST_NAME = ?,"
                            + "LAST_NAME = ?,"
                            + "GUEST_DESCRIPTION = ?,"
                            + "GUEST_DIETARY_RESTRICTIONS = ?,"
                            + "INVITE_CODE = ?,"
                            + "RESERVATION_ID = ?"
                            + " WHERE "
                            + "GUEST_ID = ?;");
            pStatement.setString(1, guest.getGivenName());
            pStatement.setString(2, guest.getSurName());
            pStatement.setString(3, guest.getDescription());
            pStatement.setString(4, guest.getDietaryRestrictions());
            pStatement.setString(5, guest.getInviteCode());

            if (guest.getReservation() == null) {
                pStatement.setNull(6, java.sql.Types.INTEGER);
            } else {
                pStatement.setInt(6, guest.getReservation().getId());
            }

            pStatement.setInt(7, guest.getId());

            int numOfRecords = pStatement.executeUpdate();

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
                if (pStatement != null) {
                    pStatement.close();
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

        PreparedStatement pStatement = null;

        try {
            pStatement = this.getUnitOfWork().createPreparedStatement(
                    "DELETE FROM wedding_jonfreer_com.GUEST WHERE GUEST_ID = ?;");
            pStatement.setInt(1, id);

            int numOfRecords = pStatement.executeUpdate();

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
                if (pStatement != null) {
                    pStatement.close();
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

        PreparedStatement pStatementInsert = null;
        PreparedStatement pStatementGetId = null;
        ResultSet result = null;

        try {
            pStatementInsert = this.getUnitOfWork().createPreparedStatement(
                    "INSERT INTO wedding_jonfreer_com.GUEST"
                            + "("
                            + "FIRST_NAME,"
                            + "LAST_NAME,"
                            + "GUEST_DESCRIPTION,"
                            + "GUEST_DIETARY_RESTRICTIONS,"
                            + "INVITE_CODE,"
                            + "RESERVATION_ID"
                            + ")"
                            + "VALUES"
                            + "(?,?,?,?,?,?);");
            pStatementInsert.setString(1, guest.getGivenName());
            pStatementInsert.setString(2, guest.getSurName());
            pStatementInsert.setString(3, guest.getDescription());
            pStatementInsert.setString(4, guest.getDietaryRestrictions());
            pStatementInsert.setString(5, guest.getInviteCode());

            if (guest.getReservation() == null) {
                pStatementInsert.setNull(6, java.sql.Types.INTEGER);
            } else {
                pStatementInsert.setInt(6, guest.getReservation().getId());
            }

            pStatementInsert.executeUpdate();

            pStatementGetId = this.getUnitOfWork().createPreparedStatement("SELECT LAST_INSERT_ID();");

            result = pStatementGetId.executeQuery();
            result.next();

            return result.getInt(1);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException(sqlEx);
        } finally {

            //release resources needed.
            try {
                if (pStatementInsert != null) {
                    pStatementInsert.close();
                }
                if (pStatementGetId != null) {
                    pStatementGetId.close();
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
     * Retrieves all guests.
     *
     * @return All of the guests.
     */
    public ArrayList<Guest> getGuests() {

        ArrayList<Guest> guests = new ArrayList<Guest>();
        PreparedStatement pStatement = null;
        ResultSet result = null;

        try {
            pStatement = this.getUnitOfWork().createPreparedStatement(
                    "SELECT "
                            + "G.GUEST_ID,"
                            + "G.FIRST_NAME,"
                            + "G.LAST_NAME,"
                            + "G.GUEST_DESCRIPTION,"
                            + "G.GUEST_DIETARY_RESTRICTIONS,"
                            + "G.INVITE_CODE,"
                            + "G.RESERVATION_ID"
                            + " FROM "
                            + "wedding_jonfreer_com.GUEST AS G;");

            result = pStatement.executeQuery();

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
                if (pStatement != null) {
                    pStatement.close();
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
}
