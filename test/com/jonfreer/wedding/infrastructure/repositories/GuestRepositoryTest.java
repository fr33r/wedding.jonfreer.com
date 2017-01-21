package com.jonfreer.wedding.infrastructure.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jonfreer.wedding.annotations.BlackBox;
import com.jonfreer.wedding.annotations.WhiteBox;
import com.jonfreer.wedding.infrastructure.exceptions.ResourceNotFoundException;
import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.domain.GuestSearchCriteria;
import com.jonfreer.wedding.domain.Reservation;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.domain.interfaces.unitofwork.IDatabaseUnitOfWork;
import com.mysql.jdbc.CallableStatement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.never;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuestRepositoryTest {

	private IDatabaseUnitOfWork databaseUnitOfWorkMock;

	@Before
	public void setUp() throws Exception {
		this.databaseUnitOfWorkMock = mock(IDatabaseUnitOfWork.class);
	}
	
	/**
	 * Verifies the code path taken for retrieving a guest
	 * that does not have a reservation associated to it.
	 * @throws SQLException Fails the test.
	 * @throws ResourceNotFoundException Fails the test.
	 */
	@WhiteBox
	@Test
	public void getGuest_verifies_guestFoundWithReservation() throws SQLException, ResourceNotFoundException{

		//constants.
		final int id = 1;
		
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);
		
		//stub mocks.
		when(resultSetMock.next()).thenReturn(true);
		when(resultSetMock.wasNull()).thenReturn(false);
		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuest(?)}")
		).thenReturn(callableStatementMock);
		when(
			callableStatementMock.executeQuery()
		).thenReturn(resultSetMock);
		
		//action.
		IGuestRepository guestRepository = new GuestRepository(this.databaseUnitOfWorkMock);
		guestRepository.getGuest(id);
		
		//verify.
		verify(this.databaseUnitOfWorkMock, times(1)).createCallableStatement("{CALL GetGuest(?)}");
		verifyNoMoreInteractions(this.databaseUnitOfWorkMock);
		
		verify(callableStatementMock, times(1)).setInt(1, id);
		verify(callableStatementMock, times(1)).executeQuery();
		verify(callableStatementMock, times(1)).isClosed();
		verify(callableStatementMock, times(1)).close();
		verifyNoMoreInteractions(callableStatementMock);
		
		verify(resultSetMock, times(1)).isClosed();
		verify(resultSetMock, times(1)).close();
		verify(resultSetMock, times(1)).next();
		verify(resultSetMock, times(1)).getInt("GUEST_ID");
		verify(resultSetMock, times(1)).getString("FIRST_NAME");
		verify(resultSetMock, times(1)).getString("LAST_NAME");
		verify(resultSetMock, times(1)).getString("GUEST_DESCRIPTION");
		verify(resultSetMock, times(1)).getString("GUEST_DIETARY_RESTRICTIONS");
		verify(resultSetMock, times(1)).getString("INVITE_CODE");
		verify(resultSetMock, times(1)).getInt("RESERVATION_ID");
		verify(resultSetMock, times(1)).wasNull();
		verifyNoMoreInteractions(resultSetMock);
	}
	
	/**
	 * Verifies the code path taken for retrieving a guest
	 * that does have a reservation associated to it.
	 * @throws SQLException Fails the test.
	 * @throws ResourceNotFoundException Fails the test.
	 */
	@WhiteBox
	@Test
	public void getGuest_verifies_guestFoundWithoutReservation() throws SQLException, ResourceNotFoundException{

		//constants.
		final int id = 1;
		
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);
		
		//stub mocks.
		when(resultSetMock.next()).thenReturn(true);
		when(resultSetMock.wasNull()).thenReturn(true);
		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuest(?)}")
		).thenReturn(callableStatementMock);
		when(
			callableStatementMock.executeQuery()
		).thenReturn(resultSetMock);
		
		//action.
		IGuestRepository guestRepository = new GuestRepository(this.databaseUnitOfWorkMock);
		guestRepository.getGuest(id);
		
		//verify.
		verify(this.databaseUnitOfWorkMock, times(1)).createCallableStatement("{CALL GetGuest(?)}");
		verifyNoMoreInteractions(this.databaseUnitOfWorkMock);
		
		verify(callableStatementMock, times(1)).setInt(1, id);
		verify(callableStatementMock, times(1)).executeQuery();
		verify(callableStatementMock, times(1)).isClosed();
		verify(callableStatementMock, times(1)).close();
		verifyNoMoreInteractions(callableStatementMock);
		
		verify(resultSetMock, times(1)).isClosed();
		verify(resultSetMock, times(1)).close();
		verify(resultSetMock, times(1)).next();
		verify(resultSetMock, times(1)).getInt("GUEST_ID");
		verify(resultSetMock, times(1)).getString("FIRST_NAME");
		verify(resultSetMock, times(1)).getString("LAST_NAME");
		verify(resultSetMock, times(1)).getString("GUEST_DESCRIPTION");
		verify(resultSetMock, times(1)).getString("GUEST_DIETARY_RESTRICTIONS");
		verify(resultSetMock, times(1)).getString("INVITE_CODE");
		verify(resultSetMock, times(1)).getInt("RESERVATION_ID");
		verify(resultSetMock, times(1)).wasNull();
		verifyNoMoreInteractions(resultSetMock);
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
	
	/**
	 * Verifies the code path taken when search criteria is not provided.
	 * @throws SQLException Fails test.
	 */
	@WhiteBox
	@Test
	public void getGuests_verifies_allGuests() throws SQLException {

		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);

		//stub mocks.
		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuests(?, ?, ?)}")
			).thenReturn(callableStatementMock);

		when(
			callableStatementMock.executeQuery()
			).thenReturn(resultSetMock);

		//action.
		IGuestRepository guestRepository = 
			new GuestRepository(this.databaseUnitOfWorkMock);
		guestRepository.getGuests(null);

		//verify.
		verify(this.databaseUnitOfWorkMock, times(1)).createCallableStatement("{CALL GetGuests(?, ?, ?)}");
		verifyNoMoreInteractions(this.databaseUnitOfWorkMock);
		
		verify(callableStatementMock, times(1)).setString(1, null);
		verify(callableStatementMock, times(1)).setString(2, null);
		verify(callableStatementMock, times(1)).setString(3, null);
		verify(callableStatementMock, times(1)).executeQuery();
		verify(callableStatementMock, times(1)).isClosed();
		verify(callableStatementMock, times(1)).close();
		verifyNoMoreInteractions(callableStatementMock);
		
		verify(resultSetMock, times(2)).next();
		verify(resultSetMock, times(1)).getInt("GUEST_ID");
		verify(resultSetMock, times(1)).getString("FIRST_NAME");
		verify(resultSetMock, times(1)).getString("LAST_NAME");
		verify(resultSetMock, times(1)).getString("GUEST_DESCRIPTION");
		verify(resultSetMock, times(1)).getString("GUEST_DIETARY_RESTRICTIONS");
		verify(resultSetMock, times(1)).getString("INVITE_CODE");
		verify(resultSetMock, times(1)).getInt("RESERVATION_ID");
		verify(resultSetMock, times(1)).wasNull();
		verify(resultSetMock, times(1)).isClosed();
		verify(resultSetMock, times(1)).close();
		verifyNoMoreInteractions(resultSetMock);
		
	}

	/**
	 * Verifies the code path taken when search criteria is provided and
	 * there are guests that match the criteria.
	 * @throws SQLException Fails test.
	 */
	@WhiteBox
	@Test
	public void getGuests_verifies_matchingGuests() throws SQLException{
		
		//constants.
		final String firstName = "Jon";
		final String lastName = "Freer";
		final String inviteCode = "PA000";
		final GuestSearchCriteria searchCriteria = 
			new GuestSearchCriteria(firstName, lastName, inviteCode);
			
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);

		when(resultSetMock.next()).thenReturn(true).thenReturn(false);
		when(
			this.databaseUnitOfWorkMock.createCallableStatement("{CALL GetGuests(?, ?, ?)}")
		).thenReturn(callableStatementMock);

		when(
			callableStatementMock.executeQuery()
		).thenReturn(resultSetMock);

		//action.
		IGuestRepository guestRepository = 
			new GuestRepository(this.databaseUnitOfWorkMock);
		guestRepository.getGuests(searchCriteria);

		//verify.
		verify(this.databaseUnitOfWorkMock, times(1)).createCallableStatement("{CALL GetGuests(?, ?, ?)}");
		verifyNoMoreInteractions(this.databaseUnitOfWorkMock);
		
		verify(callableStatementMock, times(1)).setString(1, searchCriteria.getInviteCode());
		verify(callableStatementMock, times(1)).setString(2, searchCriteria.getGivenName());
		verify(callableStatementMock, times(1)).setString(3, searchCriteria.getSurname());
		verify(callableStatementMock, times(1)).executeQuery();
		verify(callableStatementMock, times(1)).isClosed();
		verify(callableStatementMock, times(1)).close();
		verifyNoMoreInteractions(callableStatementMock);
		
		verify(resultSetMock, times(2)).next();
		verify(resultSetMock, times(1)).getInt("GUEST_ID");
		verify(resultSetMock, times(1)).getString("FIRST_NAME");
		verify(resultSetMock, times(1)).getString("LAST_NAME");
		verify(resultSetMock, times(1)).getString("GUEST_DESCRIPTION");
		verify(resultSetMock, times(1)).getString("GUEST_DIETARY_RESTRICTIONS");
		verify(resultSetMock, times(1)).getString("INVITE_CODE");
		verify(resultSetMock, times(1)).getInt("RESERVATION_ID");
		verify(resultSetMock, times(1)).wasNull();
		verify(resultSetMock, times(1)).isClosed();
		verify(resultSetMock, times(1)).close();
		verifyNoMoreInteractions(resultSetMock);
	}
	
	/**
	 * Verifies the code path taken when search criteria is provided and
	 * there are no guests that match the criteria.
	 * @throws SQLException Fails test.
	 */
	@WhiteBox
	@Test
	public void getGuests_verifies_noMatchingGuests() throws SQLException{
		
		//constants.
		final String firstName = "Jon";
		final String lastName = "Freer";
		final String inviteCode = "PA000";
		final GuestSearchCriteria searchCriteria = 
			new GuestSearchCriteria(firstName, lastName, inviteCode);
			
		//create mocks.
		CallableStatement callableStatementMock = mock(CallableStatement.class);
		ResultSet resultSetMock = mock(ResultSet.class);

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
		guestRepository.getGuests(searchCriteria);

		//verify.
		verify(this.databaseUnitOfWorkMock, times(1)).createCallableStatement("{CALL GetGuests(?, ?, ?)}");
		verifyNoMoreInteractions(this.databaseUnitOfWorkMock);
		
		verify(callableStatementMock, times(1)).setString(1, searchCriteria.getInviteCode());
		verify(callableStatementMock, times(1)).setString(2, searchCriteria.getGivenName());
		verify(callableStatementMock, times(1)).setString(3, searchCriteria.getSurname());
		verify(callableStatementMock, times(1)).executeQuery();
		verify(callableStatementMock, times(1)).isClosed();
		verify(callableStatementMock, times(1)).close();
		verifyNoMoreInteractions(callableStatementMock);
		
		verify(resultSetMock, times(1)).next();
		verify(resultSetMock, never()).getInt("GUEST_ID");
		verify(resultSetMock, never()).getString("FIRST_NAME");
		verify(resultSetMock, never()).getString("LAST_NAME");
		verify(resultSetMock, never()).getString("GUEST_DESCRIPTION");
		verify(resultSetMock, never()).getString("GUEST_DIETARY_RESTRICTIONS");
		verify(resultSetMock, never()).getString("INVITE_CODE");
		verify(resultSetMock, never()).getInt("RESERVATION_ID");
		verify(resultSetMock, never()).wasNull();
		verify(resultSetMock, times(1)).isClosed();
		verify(resultSetMock, times(1)).close();
		verifyNoMoreInteractions(resultSetMock);
	}
}
