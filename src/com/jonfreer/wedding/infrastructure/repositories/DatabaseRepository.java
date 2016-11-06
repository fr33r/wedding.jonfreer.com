package com.jonfreer.wedding.infrastructure.repositories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

public abstract class DatabaseRepository {

	private IDatabaseUnitOfWork unitOfWork;
	
	public DatabaseRepository(IDatabaseUnitOfWork unitOfWork){
		this.unitOfWork = unitOfWork;
	}
	
	public IDatabaseUnitOfWork getUnitOfWork(){
		return this.unitOfWork;
	}
}
