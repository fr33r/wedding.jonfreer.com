package com.jonfreer.wedding.infrastructure.repositories;

import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.domain.Reservation;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class GuestRepository implements IGuestRepository{

  private String connectionString;
	
  public GuestRepository(){
	
	this.connectionString = "jdbc:mysql://jonfreer.com:3306/jonfreer_wedding";
	
	try {
		java.lang.Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
	
  public Guest getGuest(int id) throws SQLException{
	  
	Guest guest = null;
	Connection connection = DriverManager.getConnection(this.connectionString);
	PreparedStatement pStatement = 
			connection.prepareStatement(
					"SELECT"
					+ "G.GUEST_ID,"
					+ "G.FIRST_NAME,"
					+ "G.LAST_NAME,"
					+ "G.GUEST_DESCRIPTION,"
					+ "G.GUEST_DIETARY_RESTRICTIONS,"
					+ "G.INVITE_CODE,"
					+ "G.RESERVATION_ID"
					+ "FROM"
					+ "jonfreer_wedding.GUEST AS G"
					+ "WHERE"
					+ "G.GUEST_ID = ?;");
	
	ResultSet result = pStatement.executeQuery();
	
	if(result.next()){
		guest = new Guest();
		guest.setId(result.getInt("GUEST_ID"));
		guest.setGivenName(result.getString("FIRST_NAME"));
		guest.setSurName(result.getString("LAST_NAME"));
		guest.setDescription(result.getString("GUEST_DESCRIPTION"));
		guest.setDietaryRestrictions(result.getString("GUEST_DIETARY_DESCRIPTION"));
		guest.setInviteCode(result.getString("INVITE_CODE"));
		
		Reservation reservation = new Reservation();
		reservation.setId(result.getInt("RESERVATION_ID"));
		
		guest.setReservation(reservation);
	}
	pStatement.close();
	connection.close();
	
	return guest;
  }

  public void updateGuest(Guest guest) throws SQLException{
	
	Connection connection = 
			DriverManager.getConnection(this.connectionString);
	PreparedStatement pStatement = 
			connection.prepareStatement(
					"UPDATE jonfreer_wedding.GUEST"
					+ "SET"
					+ "FIRST_NAME = ?,"
					+ "LAST_NAME = ?,"
					+ "GUEST_DESCRIPTION = ?,"
					+ "GUEST_DIETARY_RESTRICTIONS = ?,"
					+ "INVITE_CODE = ?,"
					+ "RESERVATION_ID = ?"
					+ "WHERE"
					+ "GUEST_ID = ?;");
	pStatement.setString(1, guest.getGivenName());
	pStatement.setString(2, guest.getSurName());
	pStatement.setString(3, guest.getDescription());
	pStatement.setString(4, guest.getDietaryRestrictions());
	pStatement.setString(5, guest.getInviteCode());
	
	if(guest.getReservation() == null){
		pStatement.setNull(6, java.sql.Types.INTEGER);
	}else{
		pStatement.setInt(6, guest.getReservation().getId());
	}
	
	int numOfRecords = pStatement.executeUpdate();
	pStatement.close();	
	connection.close();
	
	if(numOfRecords < 1){
		//throw;
	}
  }

  public void deleteGuest(int id) throws SQLException{
	  
	Connection connection = DriverManager.getConnection(this.connectionString);
	PreparedStatement pStatement = 
			connection.prepareStatement(
					"DELETE"
					+ "FROM"
					+ "jonfreer_wedding.GUEST"
					+ "WHERE"
					+ "G.GUEST_ID = ?;");
	pStatement.setInt(0, id);
	
	int numOfRecords = pStatement.executeUpdate();
	pStatement.close();
	connection.close();
	
	if(numOfRecords < 1){
		//throw;
	}
  }

  public int insertGuest(Guest guest) throws SQLException{
	  
	Connection connection = 
			DriverManager.getConnection(this.connectionString);
	PreparedStatement pStatementInsert = 
			connection.prepareStatement(
					"INSERT INTO jonfreer_wedding.GUEST"
					+ "("
					+ "FIRST_NAME,"
					+ "LAST_NAME,"
					+ "GUEST_DESCRIPTION,"
					+ "GUEST_DIETARY_RESTRICTIONS,"
					+ "INVITE_CODE,"
					+ "RESERVATION_ID"
					+ ")"
					+ "VALUES"
					+ "(?,?,?,?,?,?);");
	pStatementInsert.setString(1, guest.getGivenName());
	pStatementInsert.setString(2, guest.getSurName());
	pStatementInsert.setString(3, guest.getDescription());
	pStatementInsert.setString(4, guest.getDietaryRestrictions());
	pStatementInsert.setString(5, guest.getInviteCode());
	
	if(guest.getReservation() == null){
		pStatementInsert.setNull(6, java.sql.Types.INTEGER);
	}else{
		pStatementInsert.setInt(6, guest.getReservation().getId());
	}
	
	pStatementInsert.executeUpdate();
	pStatementInsert.close();
	
	PreparedStatement pStatementGetId = 
			connection.prepareStatement(
					"SELECT LAST_INSERT_ID();");
	
	ResultSet result = pStatementGetId.executeQuery();
	pStatementGetId.close();
	
	connection.close();
	
	return result.getInt(1);
  }
}
