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

@Service
@Named
public class GuestRepository extends DatabaseRepository implements IGuestRepository {

    public GuestRepository(IDatabaseUnitOfWork unitOfWork) {
        super(unitOfWork);
    }

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

                Reservation reservation = new Reservation();
                reservation.setId(result.getInt("RESERVATION_ID"));

                guest.setReservation(reservation);
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

    public void deleteGuest(int id) throws ResourceNotFoundException {

        PreparedStatement pStatement = null;

        try {
            pStatement = this.getUnitOfWork().createPreparedStatement(
                    "DELETE FROM wedding_jonfreer_com.GUEST WHERE G.GUEST_ID = ?;");
            pStatement.setInt(0, id);

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
