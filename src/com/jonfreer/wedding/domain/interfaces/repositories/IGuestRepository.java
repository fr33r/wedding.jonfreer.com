package com.jonfreer.wedding.domain.interfaces.repositories;

import com.jonfreer.wedding.domain.Guest;
import java.sql.*;

public interface IGuestRepository{

  Guest getGuest(int id) throws SQLException;

  void updateGuest(Guest guest) throws SQLException;

  void deleteGuest(int id) throws SQLException;

  int insertGuest(Guest guest) throws SQLException;

}
