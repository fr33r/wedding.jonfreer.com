/**
 * 
 */
package com.jonfreer.wedding.infrastructure.repositories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.jonfreer.wedding.annotations.BlackBox;
import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.domain.GuestSearchCriteria;
import com.jonfreer.wedding.domain.Reservation;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import com.mysql.jdbc.CallableStatement;

/**
 * @author jonfreer
 *
 */
public class GuestRepository_BlackBoxTest {

	private IDatabaseUnitOfWork databaseUnitOfWorkMock;

	@Before
	public void setUp() throws Exception {
		this.databaseUnitOfWorkMock = mock(IDatabaseUnitOfWork.class);
	}
	
	/**
	 * INPUT 	- 	Non-existent guest identifier.
	 * OUTCOME 	- 	ResourceNotFoundException is thrown.
	 * @throws SQLException Fails the test.
	 * @throws ResourceNotFoundException Passes the test.
	 */
	@BlackBox
	@Test(expected=ResourceNotFoundException.class)
	public void getGuest_outcomeIs_guestNotFound() throws SQLException, ResourceNotFoundException{
		
		//constants.
		final int id = -1;
		
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);
		
		//stub mocks.	
		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuest(?)}")
		).thenReturn(callableStatementMock);
		when(
			callableStatementMock.executeQuery()
		).thenReturn(resultSetMock);
		when(resultSetMock.next()).thenReturn(false);
		
		//action.
		IGuestRepository guestRepository = new GuestRepository(this.databaseUnitOfWorkMock);
		guestRepository.getGuest(id);
	}
	
	/**
	 * INPUT 	- 	Existing guest identifier.
	 * OUTCOME 	- 	Guest with matching identifier is returned.
	 * @throws SQLException Fails the test.
	 * @throws ResourceNotFoundException Fails the test.
	 */
	@BlackBox
	@Test
	public void getGuest_outcomeIs_guestFound() throws SQLException, ResourceNotFoundException{
		
		//constants.
		final int id = 1;
		final String firstName = "Jon";
		final String lastName = "Freer";
		final String description = "Groom.";
		final String dietaryRestrictions = "None.";
		final String inviteCode = "PA000";
		final int reservationId = 1;
		final Reservation expectedReservation = new Reservation();
		expectedReservation.setId(reservationId);
		final Guest expectedGuest = new Guest(
				id, firstName, lastName, description, inviteCode, 
				dietaryRestrictions, expectedReservation
			);
		
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);
		
		//stub mocks.
		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("GUEST_ID")).thenReturn(id);
		when(resultSetMock.getString("FIRST_NAME")).thenReturn(firstName);
		when(resultSetMock.getString("LAST_NAME")).thenReturn(lastName);
		when(resultSetMock.getString("GUEST_DESCRIPTION")).thenReturn(description);
		when(resultSetMock.getString("GUEST_DIETARY_RESTRICTIONS")).thenReturn(dietaryRestrictions);
		when(resultSetMock.getString("INVITE_CODE")).thenReturn(inviteCode);
		when(resultSetMock.getInt("RESERVATION_ID")).thenReturn(reservationId);		
		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuest(?)}")
		).thenReturn(callableStatementMock);
		when(
			callableStatementMock.executeQuery()
		).thenReturn(resultSetMock);
		
		//action.
		IGuestRepository guestRepository = new GuestRepository(this.databaseUnitOfWorkMock);
		Guest actualGuest = guestRepository.getGuest(id);
		
		//assert.
		assertEquals(expectedGuest, actualGuest);
	}
	
	/**
	 * INPUT	-	Null search criteria.
	 * OUTPUT	-	All guests.
	 * @throws SQLException Fails the test.
	 */
	@BlackBox
	@Test
	public void getGuests_outcomeIs_allGuests() throws SQLException{
		
		//constants.
		final int id = 1;
		final String firstName = "Jon";
		final String lastName = "Freer";
		final String description = "Groom.";
		final String dietaryRestrictions = "None.";
		final String inviteCode = "PA000";
		final int reservationId = 1;
		final Reservation expectedReservation = new Reservation();
		expectedReservation.setId(reservationId);
		final Guest expectedGuest = new Guest(
				id, firstName, lastName, description, inviteCode, 
				dietaryRestrictions, expectedReservation
			);
			
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);

		//stub mocks.
		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("GUEST_ID")).thenReturn(id);
		when(resultSetMock.getString("FIRST_NAME")).thenReturn(firstName);
		when(resultSetMock.getString("LAST_NAME")).thenReturn(lastName);
		when(resultSetMock.getString("GUEST_DESCRIPTION")).thenReturn(description);
		when(resultSetMock.getString("GUEST_DIETARY_RESTRICTIONS")).thenReturn(dietaryRestrictions);
		when(resultSetMock.getString("INVITE_CODE")).thenReturn(inviteCode);
		when(resultSetMock.getInt("RESERVATION_ID")).thenReturn(reservationId);

		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuests(?, ?, ?)}")
		).thenReturn(callableStatementMock);

		when(
			callableStatementMock.executeQuery()
		).thenReturn(resultSetMock);

		//action.
		IGuestRepository guestRepository = 
			new GuestRepository(this.databaseUnitOfWorkMock);

		ArrayList<Guest> actualGuests = guestRepository.getGuests(null);
		
		//assert.
		assertEquals(1, actualGuests.size());
		assertEquals(expectedGuest, actualGuests.get(0));
	}
	
	/**
	 * INPUT	-	Search criteria.
	 * OUTPUT	-	Guests matching the search criteria.
	 * @throws SQLException Fails test.
	 */
	@BlackBox
	@Test
	public void getGuests_outcomeIs_matchingGuests() throws SQLException{
		
		//constants.
		final int id = 1;
		final String firstName = "Jon";
		final String lastName = "Freer";
		final String description = "Groom.";
		final String dietaryRestrictions = "None.";
		final String inviteCode = "PA000";
		final int reservationId = 1;
		final Reservation expectedReservation = new Reservation();
		expectedReservation.setId(reservationId);
		final Guest expectedGuest = new Guest(
				id, firstName, lastName, description, inviteCode, 
				dietaryRestrictions, expectedReservation
			);
		final GuestSearchCriteria searchCriteria = 
			new GuestSearchCriteria(firstName, lastName, inviteCode);
			
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);

		//stub mocks.
		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(resultSetMock.getInt("GUEST_ID")).thenReturn(id);
		when(resultSetMock.getString("FIRST_NAME")).thenReturn(firstName);
		when(resultSetMock.getString("LAST_NAME")).thenReturn(lastName);
		when(resultSetMock.getString("GUEST_DESCRIPTION")).thenReturn(description);
		when(resultSetMock.getString("GUEST_DIETARY_RESTRICTIONS")).thenReturn(dietaryRestrictions);
		when(resultSetMock.getString("INVITE_CODE")).thenReturn(inviteCode);
		when(resultSetMock.getInt("RESERVATION_ID")).thenReturn(reservationId);

		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuests(?, ?, ?)}")
		).thenReturn(callableStatementMock);

		when(
			callableStatementMock.executeQuery()
		).thenReturn(resultSetMock);

		//action.
		IGuestRepository guestRepository = 
			new GuestRepository(this.databaseUnitOfWorkMock);

		ArrayList<Guest> actualGuests = guestRepository.getGuests(searchCriteria);
		
		//assert.
		assertEquals(1, actualGuests.size());
		assertEquals(expectedGuest, actualGuests.get(0));
	}
	
	/**
	 * INPUT	-	Search criteria.
	 * OUTPUT	-	No matching guests.
	 * @throws SQLException Fails test.
	 */
	@BlackBox
	@Test
	public void getGuests_outcomeIs_noMatchingGuests() throws SQLException{
		
		//constants.
		final String firstName = "Jon";
		final String lastName = "Freer";
		final String inviteCode = "PA000";
		final int reservationId = 1;
		final Reservation expectedReservation = new Reservation();
		expectedReservation.setId(reservationId);
		final GuestSearchCriteria searchCriteria = 
			new GuestSearchCriteria(firstName, lastName, inviteCode);
			
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);

		//stub mocks.
		when(resultSetMock.next()).thenReturn(false);

		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuests(?, ?, ?)}")
		).thenReturn(callableStatementMock);

		when(
			callableStatementMock.executeQuery()
		).thenReturn(resultSetMock);

		//action.
		IGuestRepository guestRepository = 
			new GuestRepository(this.databaseUnitOfWorkMock);

		ArrayList<Guest> actualGuests = guestRepository.getGuests(searchCriteria);
		
		//assert.
		assertEquals(0, actualGuests.size());
	}

}
