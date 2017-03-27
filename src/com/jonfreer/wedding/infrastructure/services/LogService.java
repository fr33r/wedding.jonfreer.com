package com.jonfreer.wedding.infrastructure.services;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IDatabaseUnitOfWorkFactory;

/**
 * 
 * @author jonfreer
 * @since 03/26/2017
 */
@Service
public class LogService implements com.jonfreer.wedding.infrastructure.interfaces.services.LogService {

	/**
	 * 
	 * @author jonfreer
	 * @since 03/26/2017
	 */
	private enum LogLevel{
		DEBUG,
		INFO,
		WARNING,
		ERROR
	}
	
	private final IDatabaseUnitOfWorkFactory unitOfWorkFactory;
	
	@Inject
	public LogService(IDatabaseUnitOfWorkFactory unitOfWorkFactory){
		this.unitOfWorkFactory = unitOfWorkFactory;
	}
	
	@Override
	public void error(String message, String stacktrace) {
		this.log(LogLevel.ERROR, message, stacktrace);
	}

	@Override
	public void error(Exception exception) {
		this.log(LogLevel.ERROR, exception.getLocalizedMessage(), this.getStackTrace(exception));
	}

	@Override
	public void warning(String message) {
		this.log(LogLevel.WARNING, message);
	}

	@Override
	public void info(String message) {
		this.log(LogLevel.INFO, message);
	}

	@Override
	public void debug(String message) {
		this.log(LogLevel.DEBUG, message);
	}
	
	private void log(LogLevel level, String message){
		this.log(level, message, null);
	}
	
	private void log(LogLevel level, String message, String stacktrace){
		
		IDatabaseUnitOfWork unitOfWork = this.unitOfWorkFactory.create();	
		CallableStatement createStatement = null;

        try {
            createStatement = unitOfWork.createCallableStatement("{ CALL CreateLog(?, ?, ?) }");
            createStatement.setString(1, level.toString());
            createStatement.setString(2, message);
            
            if(stacktrace != null){
            	createStatement.setString(3, stacktrace);
            }else{
            	createStatement.setNull(3, Types.VARCHAR);
            }
            
            createStatement.executeUpdate();
            unitOfWork.Save();

        } catch (SQLException e) {
        	unitOfWork.Undo();
            e.printStackTrace();
        } finally {
            try {
                if (createStatement != null && !createStatement.isClosed()) {
                    createStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	private String getStackTrace(Exception exception){
		
		StringBuilder stackTrace = new StringBuilder();
		
		for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
			stackTrace
				.append("at " + stackTraceElement.getClassName() + ".")
				.append(stackTraceElement.getMethodName())
				.append("(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")")
				.append("\n");
        }
		
		return stackTrace.toString();
	}

}
