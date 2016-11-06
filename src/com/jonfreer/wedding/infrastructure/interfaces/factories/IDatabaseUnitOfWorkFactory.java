package com.jonfreer.wedding.infrastructure.interfaces.factories;

import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

public interface IDatabaseUnitOfWorkFactory {
	
	IDatabaseUnitOfWork create();
	
}
