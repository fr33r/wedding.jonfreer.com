package com.jonfreer.wedding.infrastructure.factories;

import com.jonfreer.wedding.domain.interfaces.repositories.IReservationRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.interfaces.factories.IReservationRepositoryFactory;
import com.jonfreer.wedding.infrastructure.repositories.ReservationRepository;
import org.jvnet.hk2.annotations.Service;
import javax.inject.Named;

@Service @Named
public class ReservationRepositoryFactory implements IReservationRepositoryFactory {

	@Override
	public IReservationRepository create(IDatabaseUnitOfWork unitOfWork) {
		return new ReservationRepository(unitOfWork);
	}

}
