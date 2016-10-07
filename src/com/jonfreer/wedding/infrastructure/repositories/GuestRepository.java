package com.jonfreer.wedding.infrastructure.repositories;

import com.jonfreer.wedding.domain.Guest;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.mysql.jdbc.*;

public class GuestRepository implements IGuestRepository{

	private String connectionString;
	private String username;
	private String password;
	
	public GuestRepository(String connectionString, String username, String password){
		
		this.connectionString = connectionString;
		this.username = username;
		this.password = password;
		
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
  public Guest getGuest(int id){
    return null;
  }

  public void updateGuest(Guest guest){
    return;
  }

  public void deleteGuest(int id){
    return;
  }

  public int insertGuest(Guest guest){
    return -1;
  }

}
