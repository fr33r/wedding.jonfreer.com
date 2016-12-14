package com.jonfreer.wedding.infrastructure.repositories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.repositories.IExceptionRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A database repository that directly interacts with the database
 * to manage exception entities.
 *
 * @author jonfreer
 * @since 12/11/16
 */
public class ExceptionRepository extends DatabaseRepository implements IExceptionRepository {

    /**
     * Constructs a new instance provided an instance of a class that
     * implements the IDatabaseUnitOfWork interface. It is recommended
     * that instead of invoking this constructor, instead use the
     * ExceptionRepositoryFactory class to create an instance.
     *
     * @param unitOfWork An instance of a class that implements the
     *                   IDatabaseUnitOfWork interface. All methods invoked
     *                   on the ExceptionRepository instance being created will
     *                   utilize this unit of work.
     */
    public ExceptionRepository(IDatabaseUnitOfWork unitOfWork) {
        super(unitOfWork);
    }

    /**
     * Creates a new exception in the exception repository.
     *
     * @param exception The exception to create in the exception repository.
     * @return An identifier for the exception created.
     */
    @Override
    public int create(Exception exception) {
        PreparedStatement createStatement = null;
        PreparedStatement getIdStatement = null;
        ResultSet result = null;
        StringBuilder builder = new StringBuilder();

        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            builder.append("at " + stackTraceElement.getClassName() + ".");
            builder.append(stackTraceElement.getMethodName());
            builder.append("(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")");
            builder.append("\n");
        }

        try {
            createStatement = this.getUnitOfWork().createPreparedStatement(
                    "INSERT INTO EXCEPTION (MESSAGE, STACKTRACE) VALUES (?,?);");
            createStatement.setString(1, exception.getMessage());
            createStatement.setString(2, builder.toString());
            createStatement.executeUpdate();

            getIdStatement = this.getUnitOfWork().createPreparedStatement(
                    "SELECT LAST_INSERT_ID();");
            result = getIdStatement.executeQuery();
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (createStatement != null) {
                    createStatement.close();
                }
                if (getIdStatement != null) {
                    getIdStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
