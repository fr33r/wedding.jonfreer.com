package com.jonfreer.wedding.application.interfaces.services;

import com.jonfreer.wedding.domain.Guest;

public interface IGuestService{

  Guest getGuest(int id);

  void updateGuest(Guest guest);

  void deleteGuest(int id);

  int insertGuest(Guest guest);

}
