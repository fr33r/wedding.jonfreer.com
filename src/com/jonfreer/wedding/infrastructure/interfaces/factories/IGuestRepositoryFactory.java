package com.jonfreer.wedding.infrastructure.interfaces.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface IGuestRepositoryFactory {
	
	IGuestRepository create(IDatabaseUnitOfWork unitOfWork);
	
}
