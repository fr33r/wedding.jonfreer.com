package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.infrastructure.unitofwork.DatabaseUnitOfWork;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUnitOfWorkFactory implements IDatabaseUnitOfWorkFactory{

	private String connectionString;
	private String username;
	private String password;
	
	public DatabaseUnitOfWorkFactory(String connectionString, String username, String password){
		this.connectionString = connectionString;
		this.username = username;
		this.password = password;
	}

	@Override
	public IDatabaseUnitOfWork create() {
		try {
			return new DatabaseUnitOfWork(
				DriverManager.getConnection(this.connectionString, this.username, this.password));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new java.lang.RuntimeException(e);
		}
	}
}
