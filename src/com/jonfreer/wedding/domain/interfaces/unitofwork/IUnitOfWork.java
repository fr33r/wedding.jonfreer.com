package com.jonfreer.wedding.domain.interfaces.unitofwork;

public interface IUnitOfWork {

	void Save();
	
	void Undo();
	
}
