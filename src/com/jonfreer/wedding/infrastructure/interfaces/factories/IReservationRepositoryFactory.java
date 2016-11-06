package com.jonfreer.wedding.infrastructure.interfaces.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IReservationRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

public interface IReservationRepositoryFactory {
	
	IReservationRepository create(IDatabaseUnitOfWork unitOfWork);
	
}
