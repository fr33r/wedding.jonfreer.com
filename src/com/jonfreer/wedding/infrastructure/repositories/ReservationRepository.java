package com.jonfreer.wedding.infrastructure.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jonfreer.wedding.domain.Reservation;
import com.jonfreer.wedding.domain.interfaces.repositories.IReservationRepository;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

import javax.inject.Named;

import org.jvnet.hk2.annotations.Service;

/**
 * A database repository that directly interacts with the database
 * to manage reservation entities.
 */
@Service
@Named
public class ReservationRepository extends DatabaseRepository implements IReservationRepository {

    /**
     * Constructs a new instance provided an instance of a class that
     * implements the IDatabaseUnitOfWork interface. It is recommended
     * that instead of invoking this constructor, instead use the
     * ReservationRepositoryFactory class to create an instance.
     *
     * @param unitOfWork An instance of a class that implements the
     *                   IDatabaseUnitOfWork interface. All methods invoked
     *                   on the ReservationRepository instance being created will
     *                   utilize this unit of work.
     */
    public ReservationRepository(IDatabaseUnitOfWork unitOfWork) {
        super(unitOfWork);
    }

    /**
     * Retrieves a reservation that is identified by the identifier provided.
     *
     * @param id The identifier of the reservation to be retrieved.
     * @return An instance of Reservation that has the identifier specified.
     * @throws ResourceNotFoundException Thrown when a reservation with the identifier
     *                                   provided cannot be found.
     */
    @Override
    public Reservation getReservation(int id) throws ResourceNotFoundException {

        Reservation reservation = null;
        PreparedStatement pStatement = null;
        ResultSet result = null;

        try {
            pStatement = this.getUnitOfWork().createPreparedStatement(
                    "SELECT "
                            + "R.RESERVATION_ID,"
                            + "R.DATETIME_SUBMITTED,"
                            + "R.IS_ATTENDING"
                            + " FROM "
                            + "wedding_jonfreer_com.RESERVATION AS R"
                            + " WHERE "
                            + "R.RESERVATION_ID = ?;");

            pStatement.setInt(1, id);
            result = pStatement.executeQuery();

            if (result.next()) {
                reservation = new Reservation();
                reservation.setId(result.getInt("RESERVATION_ID"));
                reservation.setSubmittedDateTime(result.getDate("DATETIME_SUBMITTED"));
                reservation.setIsAttending(result.getBoolean("IS_ATTENDING"));
            }

            if (reservation == null) {
                throw new ResourceNotFoundException(
                        "A guest with an ID of '" + id + "' could not be found.", id);
            }

            return reservation;

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
     * Replaces the state of an existing reservation with the state of the reservation provided.
     *
     * @param desiredReservationState The desired state of the reservation to update.
     * @throws ResourceNotFoundException Thrown when a reservation with the identifier
     *                                   provided in the desired state cannot be found.
     */
    @Override
    public void updateReservation(Reservation desiredReservationState) throws ResourceNotFoundException {

        PreparedStatement pStatement = null;

        try {
            pStatement = this.getUnitOfWork().createPreparedStatement(
                    "UPDATE wedding_jonfreer_com.RESERVATION"
                            + " SET "
                            + "DATETIME_SUBMITTED = ?,"
                            + "IS_ATTENDING = ?"
                            + " WHERE "
                            + "RESERVATION_ID = ?;");
            pStatement.setDate(1, new java.sql.Date(desiredReservationState.getSubmittedDateTime().getTime()));
            pStatement.setBoolean(2, desiredReservationState.getIsAttending());
            pStatement.setInt(3, desiredReservationState.getId());

            int numOfRecords = pStatement.executeUpdate();

            if (numOfRecords < 1) {
                throw new ResourceNotFoundException(
                        "A reservation with an ID of '" + desiredReservationState.getId() +
                                "' could not be found.", desiredReservationState.getId());
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
     * Creates a new reservation with the state provided.
     *
     * @param desiredReservationState The desired state of the reservation to create.
     * @return The identifier of the newly created reservation.
     */
    @Override
    public int insertReservation(Reservation desiredReservationState) {

        PreparedStatement pStatementInsert = null;
        PreparedStatement pStatementGetId = null;
        ResultSet result = null;

        try {
            pStatementInsert = this.getUnitOfWork().createPreparedStatement(
                    "INSERT INTO wedding_jonfreer_com.RESERVATION"
                            + "("
                            + "DATETIME_SUBMITTEDD,"
                            + "IS_ATTENDING"
                            + ")"
                            + "VALUES"
                            + "(?,?);");
            pStatementInsert.setDate(1, new java.sql.Date(desiredReservationState.getSubmittedDateTime().getTime()));
            pStatementInsert.setBoolean(2, desiredReservationState.getIsAttending());

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
     * Deletes a reservation that is identified by the identifier provided.
     *
     * @param id The identifier of the reservation to be deleted.
     * @throws ResourceNotFoundException Thrown when a reservation with the identifier
     *                                   provided cannot be found.
     */
    @Override
    public void deleteReservation(int id) throws ResourceNotFoundException {

        PreparedStatement pStatement = null;

        try {
            pStatement = this.getUnitOfWork().createPreparedStatement(
                    "DELETE FROM wedding_jonfreer_com.RESERVATION WHERE G.RESERVATION_ID = ?;");
            pStatement.setInt(0, id);

            int numOfRecords = pStatement.executeUpdate();

            if (numOfRecords < 1) {
                throw new ResourceNotFoundException(
                        "A reservation with an ID of '" + id + "' could not be found.", id);
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
}
