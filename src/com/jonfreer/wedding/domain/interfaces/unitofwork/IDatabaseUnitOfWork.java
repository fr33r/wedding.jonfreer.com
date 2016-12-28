package com.jonfreer.wedding.domain.interfaces.unitofwork;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;

/**
 * Defines the contract for any class or interface that wishes
 * to represent a unit of work in the context of a database.
 */
public interface IDatabaseUnitOfWork extends IUnitOfWork {

    /**
     * Constructs a prepared statement given an SQL string.
     *
     * @param sql The parameterized SQL statement.
     * @return A prepared statement that can be used to contribute
     * to the unit of work.
     */
    PreparedStatement createPreparedStatement(String sql);

    /**
     * Constructs a callable statement given an SQL string.
     * @param sql The JDBC escaped syntax SQL statement.
     * @return A callable statement that cab be used to contribute
     * to the unit of work.
     */
    CallableStatement createCallableStatement(String sql);
}
