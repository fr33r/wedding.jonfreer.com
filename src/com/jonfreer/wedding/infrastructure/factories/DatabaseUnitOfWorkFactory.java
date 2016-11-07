package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;
import com.jonfreer.wedding.infrastructure.unitofwork.DatabaseUnitOfWork;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseUnitOfWorkFactory implements IDatabaseUnitOfWorkFactory{

	private String connectionString;
	private String username;
	private String password;

	public DatabaseUnitOfWorkFactory(){

		InputStream is = null;

		try {
			
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream("databaseInfo.properties");
			Properties databaseProperties = new Properties();
			databaseProperties.load(is);
			is.close();
			
			this.connectionString = databaseProperties.getProperty("connectionString");
			this.username = databaseProperties.getProperty("username");
			this.password = databaseProperties.getProperty("password");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if(is != null){ is.close(); }
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
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
