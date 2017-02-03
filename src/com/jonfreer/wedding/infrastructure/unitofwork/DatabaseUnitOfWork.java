package com.jonfreer.wedding.infrastructure.unitofwork;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

import java.sql.Connection;

/**
 * Represents a unit of work in the context of database interactions.
 */
public class DatabaseUnitOfWork implements IDatabaseUnitOfWork {

    private Connection connection;

    /**
     * Constructs a new instance provided an instance of Connection. It is
     * in the context of this connection that the unit of work will be utilized.
     * It is recommended that an instance of this class is created by using the
     * DatabaseUnitOfWorkFactory class as opposed to invoking this constructor directly.
     *
     * @param connection The connection that this DatabaseUnitOfWork instance
     *                   will be used with.
     */
    public DatabaseUnitOfWork(Connection connection) {
        this.connection = connection;
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves (commits) the unit of work to the database.
     */
    @Override
    public void Save() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Undoes (performs a rollback for) the unit of work.
     */
    @Override
    public void Undo() {
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Constructs an instance of PreparedStatement in the context of
     * this unit of work. Use this method to add changes to the unit of
     * work.
     *
     * @param sql The parameterized SQL to execute during this unit of work.
     * @return An instance of PreparedStatement representing a single parameterized
     * SQL statement to execute for this unit of work.
     */
    @Override
    public PreparedStatement createPreparedStatement(String sql) {
        try {
            return this.connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new java.lang.RuntimeException(e);
        }
    }

    /**
     * Constructs an instance of CallableStatement in the context of
     * this unit of work. Use this method to add changes to the unit of
     * work when working with stored procedures.
     *
     * @param sql The JDBC escaped syntax SQL statement.
     * @return An instance of CallableStatement representing a single JDBC
     * escape syntax SQL statement to execute for this unit of work.
     */
    @Override
    public CallableStatement createCallableStatement(String sql){
        try{
            return this.connection.prepareCall(sql);
        }catch(SQLException e){
            e.printStackTrace();
            throw new java.lang.RuntimeException(e);
        }
    }
}
