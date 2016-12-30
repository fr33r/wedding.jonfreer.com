package com.jonfreer.wedding.infrastructure.repositories;

import java.sql.*;

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
        CallableStatement cStatement = null;
        ResultSet result = null;

        try {
            cStatement =
                this.getUnitOfWork().createCallableStatement("{CALL GetReservation(?)}");

            cStatement.setInt(1, id);
            result = cStatement.executeQuery();

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
     * Replaces the state of an existing reservation with the state of the reservation provided.
     *
     * @param desiredReservationState The desired state of the reservation to update.
     * @throws ResourceNotFoundException Thrown when a reservation with the identifier
     *                                   provided in the desired state cannot be found.
     */
    @Override
    public void updateReservation(Reservation desiredReservationState) throws ResourceNotFoundException {

        CallableStatement cStatement = null;

        try {
            cStatement =
                this.getUnitOfWork().createCallableStatement("{CALL UpdateReservation(?, ?, ?)}");
            cStatement.setInt(1, desiredReservationState.getId());
            cStatement.setDate(2, new java.sql.Date(desiredReservationState.getSubmittedDateTime().getTime()));
            cStatement.setBoolean(3, desiredReservationState.getIsAttending());

            int numOfRecords = cStatement.executeUpdate();

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
     * Creates a new reservation with the state provided.
     *
     * @param desiredReservationState The desired state of the reservation to create.
     * @return The identifier of the newly created reservation.
     */
    @Override
    public int insertReservation(Reservation desiredReservationState) {

        CallableStatement cStatement = null;
        ResultSet result = null;

        try {
            cStatement =
                this.getUnitOfWork().createCallableStatement("{CALL CreateReservation(?, ?)}");
            cStatement.setBoolean(1, desiredReservationState.getIsAttending());
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
     * Deletes a reservation that is identified by the identifier provided.
     *
     * @param id The identifier of the reservation to be deleted.
     * @throws ResourceNotFoundException Thrown when a reservation with the identifier
     *                                   provided cannot be found.
     */
    @Override
    public void deleteReservation(int id) throws ResourceNotFoundException {

        CallableStatement cStatement = null;

        try {
            cStatement =
                this.getUnitOfWork().createCallableStatement("{CALL DeleteReservation(?)}");
            cStatement.setInt(1, id);

            int numOfRecords = cStatement.executeUpdate();

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
                if (cStatement != null) {
                    cStatement.close();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                throw new RuntimeException(sqlEx);
            }
        }
    }
}
