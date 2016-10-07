package com.jonfreer.wedding.application.services;

import com.jonfreer.wedding.application.interfaces.services.IGuestService;
import com.jonfreer.wedding.domain.interfaces.repositories.IGuestRepository;
import com.jonfreer.wedding.domain.Guest;

public class GuestService implements IGuestService{

  private IGuestRepository guestRepository;

  public GuestService(IGuestRepository guestRepository){
    this.guestRepository = guestRepository;
  }

  public Guest getGuest(int id){
    try{
      return this.guestRepository.getGuest(id);
    }catch(java.lang.Exception ex){

    }
  }

  public void updateGuest(Guest guest){
    try{
      this.guestRepository.updateGuest(guest);
    }catch(java.lang.Exception ex){

    }
  }

  public void deleteGuest(int id){
    try{
      this.guestRepository.deleteGuest(id);
    }catch(java.lang.Exception ex){

    }
  }

  public int insertGuest(Guest guest){
    try{
      return this.guestRepository.insertGuest(guest);
    }catch(java.lang.Exception ex){

    }
  }

}
