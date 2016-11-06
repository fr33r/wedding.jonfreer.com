package com.jonfreer.wedding.infrastructure.unitofwork;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import java.sql.Connection;

public class DatabaseUnitOfWork implements IDatabaseUnitOfWork{

	private Connection connection;
	
	public DatabaseUnitOfWork(Connection connection){
		this.connection = connection;
		try {
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void Save() {
		try {
			this.connection.commit();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				this.connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}	
	}

	@Override
	public void Undo() {
		try {
			this.connection.rollback();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try{
				this.connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public PreparedStatement createPreparedStatement(String sql) {
		try {
			return this.connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new java.lang.RuntimeException(e);
		}
	}

}
