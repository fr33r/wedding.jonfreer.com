package com.jonfreer.wedding.infrastructure.repositories;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;

public class DatabaseRepositoryTest {

	@Test
	public void Given_Database_Unit_Of_Work_Then_Get_Unit_Of_Work() {
		
		//arrange.
		IDatabaseUnitOfWork databaseUnitOfWork = mock(IDatabaseUnitOfWork.class);
		
		//action.
		DatabaseRepository databaseRepository = new GuestRepository(databaseUnitOfWork);
		IDatabaseUnitOfWork actualDatabaseUnitOfWork = 
				databaseRepository.getUnitOfWork();
		
		//assert.
		assertEquals(databaseUnitOfWork, actualDatabaseUnitOfWork);
		
	}

}
