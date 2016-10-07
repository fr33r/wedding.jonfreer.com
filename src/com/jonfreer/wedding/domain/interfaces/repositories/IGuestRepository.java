package com.jonfreer.wedding.domain.interfaces.repositories;

import com.jonfreer.wedding.domain.Guest;

public interface IGuestRepository{

  Guest getGuest(int id);

  void updateGuest(Guest guest);

  void deleteGuest(int id);

  int insertGuest(Guest guest);

}
